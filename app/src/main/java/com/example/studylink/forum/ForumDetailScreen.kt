package com.example.studylink.forum

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.studylink.R
import com.example.studylink.Realusers
import com.example.studylink.currUser
import com.example.studylink.defaultColor
import com.example.studylink.dividerColor
import com.example.studylink.headText
import com.example.studylink.inRefresh
import com.example.studylink.subheadText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Closeable
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.Date
import java.util.Locale


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForumDetailScreen(
    forumId: String,
    forumViewModel: ForumViewModel = viewModel(),
    navController: NavController
) {
    val forumUiState by forumViewModel.uiState.collectAsState()
    val forum: ForumModel = forumUiState.activeForum!!
    var isSheetExpanded by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(isLoading) {
        // Perform operations serially
        delay(1000)
        forumViewModel.getForumDetail(forumId)
        forumViewModel.getForumCommentByForumID(forumId)
        // Update isLoading after both operations are completed
        isLoading = false
        Log.d("TAMPILANFORUM", forum.toString())
    }

    if  (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        )
    } else {
        BackHandler(
            enabled = isSheetExpanded,
            onBack = {
                isSheetExpanded = false
            }
        )

        val coroutineScope = rememberCoroutineScope()
        val isScrolling = remember { mutableStateOf(false) }
        val listState = rememberLazyListState()

        LaunchedEffect(listState) {
            snapshotFlow { listState.isScrollInProgress }.collect { scrollInProgress ->
                if (scrollInProgress && !isScrolling.value) {
                    isScrolling.value = true
                } else if (!scrollInProgress && isScrolling.value) {
                    coroutineScope.launch {
                        delay(300)
                        isScrolling.value = false
                    }
                }
            }
        }

        Scaffold(
            containerColor = defaultColor,
            floatingActionButton = {
                AnimatedVisibility(
                    visible = !isScrolling.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FloatingActionButton(
                        onClick = { isSheetExpanded = true },
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }
            }
        ) { paddingValue ->
            Column {
                ForumDetailTopBar(
                    onClickBack = {
                        forumViewModel.goAllForum(navController)
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
//                    .padding(16.dp)
                        .padding(paddingValue),
                    state = listState,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ForumTitle(
                            forum,
                            forumUiState,
                            onClickComment = {
                                isSheetExpanded = true
                            },
                            onClickLike = {

                            }
                        )
                        //                    VoteButton(
//                        upvote = forum.upvote.size,
//                        isVoted = forumViewModel.isVoteForumByUserId(0, currUser.value.email),
//                        voteClick = { forumViewModel.voteForum(0, currUser.value.email) }
//                    )
                    }
                    // Display comment here
                    itemsIndexed(forumUiState.commentList) { index, item ->
                        CommentItem(
                            comment = item,
                            onVoteClick = { forumViewModel.voteComment(index, currUser.value.email , forum.documentId) },
                            isVoted = forumViewModel.isVoteCommentByUserId(index,currUser.value.email ),
                            upvote = item.upvote.size)
                        Divider(modifier = Modifier.fillMaxWidth(0.95f), color = dividerColor)
                    }
                }
            }
            if (isSheetExpanded) {
                ForumSheet(
                    commentText = commentText,
                    onCommentTextChanged = { commentText = it },
                    onSendComment = {
                        forumViewModel.addForumComment(commentText)
                        isSheetExpanded = false // Close the sheet after sending
                        isLoading = true
                    },
                    onDismiss = { isSheetExpanded = false }
                )
            }
        }
    }
}

@Composable
fun ForumDetailTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 60.dp)
            .background(color = defaultColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = "Back Button",
                    tint = headText,
                    modifier = Modifier
                        .requiredWidth(width = 10.dp)
                        .requiredHeight(height = 18.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                onClickBack.invoke()
                            }
                        )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Back",
                    color = headText,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal)
                )
                Text(text = inRefresh.value.toString(), color = Color.Transparent)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForumTitle(
    forum: ForumModel,
    forumUiState: ForumUiState,
    onClickComment: () -> Unit,
    onClickLike: () -> Unit
) {
    var userData = Realusers.firstOrNull{ forum.authorId == it.email }
    if (userData == null) {
        userData = currUser.value
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Display title
            Text(
                text = forum.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = headText,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display author
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(userData!!.imageURL),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = userData!!.fullName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = subheadText
                )
            }

            // Display text content
            Text(
                text = forum.text,
                style = TextStyle(
                    fontSize = 13.sp
                ),
                color = headText,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Display tags if available
            // Tags
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(bottom = 5.dp)
            ) {
                items(forum.tags) { tag ->
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(color = Color(0xFFFFC600))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .align(alignment = Alignment.Start)
                            .height(20.dp),
                        text = tag,
                        style = MaterialTheme.typography.titleMedium,
                        color = headText,
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row {
                Text(
                    text = forum.timestamp?.let { timestamp ->
                        val date = timestamp.toDate()
                        val formattedDate = SimpleDateFormat("HH:mm · MMM dd, yyyy", Locale.getDefault()).format(date)
                        formattedDate
                    } ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = subheadText
                )
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = dividerColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onClickComment.invoke()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chaticon),
                        contentDescription = "Comment",
                        tint = subheadText,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = forumUiState.commentList.size.toString(),
                    color = subheadText,
                    style = TextStyle(
                        fontSize = 15.sp
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onClickLike.invoke()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUpOffAlt,
                        contentDescription = "Thumbs Up",
                        tint = subheadText,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = forum.upvote.size.toString(),
                    color = subheadText,
                    style = TextStyle(
                        fontSize = 15.sp
                    )
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth(),
            color = dividerColor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentItem(
    comment: CommentModel,
    onVoteClick: () -> Unit,
    isVoted: Boolean,
    upvote: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        val userData = Realusers.firstOrNull{ comment.userId == it.email }

        val now = Instant.now()
        val commentTime = comment.timestamp?.toDate()?.toInstant()
        val duration = Duration.between(commentTime, now)

        val weeks = duration.toDays() / 7
        val days = duration.toDays() % 7
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        val timeDifference = when {
            weeks > 0 -> "${weeks}w"
            days > 0 -> "${days}d"
            hours > 0 -> "${hours}h"
            minutes > 0 -> "${minutes}m"
            else -> "${seconds}s"
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (userData != null) {
                    Image(
                        painter = rememberAsyncImagePainter(userData!!.imageURL),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .align(Alignment.Top)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${userData!!.fullName}",
                                style = TextStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = headText
                                )
                            )
                            Text(
                                text = " · ",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = subheadText
                            )
                            Text(
                                text = timeDifference,
//                                    text = comment.timestamp?.let { timestamp ->
//                                        val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//                                            .format(Date(timestamp.seconds * 1000))
//                                        formattedDate
//                                    } ?: "",
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = subheadText
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = comment.text,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = headText
                            )
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                IconButton(
                    onClick = onVoteClick ,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(28.dp)
                ) {
                    val icon = if (isVoted) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp
                    val tint = if (isVoted) Color(44, 145, 72, 255) else headText
                    Icon(
                        imageVector = icon,
                        contentDescription = "Thumbs Up",
                        tint = tint,
                        modifier = Modifier
                            .size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = upvote.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = subheadText
                    )
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumSheet(
    commentText: String,
    onCommentTextChanged: (String) -> Unit,
    onSendComment: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        windowInsets = WindowInsets.ime,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = commentText,
                onValueChange = { onCommentTextChanged(it) },
                label = { Text("Enter your comment") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSendComment,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Send Comment")
            }
        }
    }
}


@Composable
fun CommentBlock() {

}

@Composable
fun FormComponent() {

}


