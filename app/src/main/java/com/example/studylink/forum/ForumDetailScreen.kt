package com.example.studylink.forum

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ForumDetailScreen(
    forumId: String,
    forumViewModel: ForumViewModel = viewModel(),
) {
    val forumUiState by forumViewModel.uiState.collectAsState()
    val forum: ForumModel = forumUiState.activeForum!!
    var isSheetExpanded by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true)}

    LaunchedEffect(isLoading) {
        // Perform operations serially
        val forumDetail = withContext(Dispatchers.IO) {
            forumViewModel.getForumDetail(forumId)
        }

        val forumComments = withContext(Dispatchers.IO) {
            forumViewModel.getForumCommentByForumID(forumId)
        }
        // Update isLoading after both operations are completed
        isLoading = false
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

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { isSheetExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ){ paddingValue ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
//                .padding(16.dp)
                    .padding(paddingValue)
            ) {
                item {
                    // Forum Title
                    ForumTitle(forum)
                }
                // Display comment here
                items(forumUiState.commentList) { item ->
                    CommentItem(item)
                }



                item {
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
    }



}

@Composable
fun ForumTitle(forum: ForumModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display title
        Text(
            text = forum.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display author and timestamp in a Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "By ${forum.authorId}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = forum.timestamp?.let {
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(it.seconds * 1000))
                } ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // Display text content
        Text(
            text = forum.text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Display tags if available
        // Tags
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(bottom = 5.dp)
        ){
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
                    color = Color.Black,
                )
            }
        }

        // Display upvote count
        Text(
            text = "Upvotes: ${forum.upvote}",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun CommentItem(comment: CommentModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = comment.text,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Upvotes: ${comment.upvote}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
                Text(
                    text = "User ID: ${comment.userId}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.timestamp?.let { timestamp ->
                    val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(Date(timestamp.seconds * 1000))
                    formattedDate
                } ?: "",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )
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


