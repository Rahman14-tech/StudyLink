package com.example.studylink.forum

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studylink.BottomBar
import com.example.studylink.BottomContent
import com.example.studylink.CustomTextField
import com.example.studylink.QNA
import com.example.studylink.R
import com.example.studylink.background
import com.example.studylink.cardsColor
import com.example.studylink.coloringSchema
import com.example.studylink.currUser
import com.example.studylink.defaultColor
import com.example.studylink.groupButtonColor
import com.example.studylink.headText
import com.example.studylink.inRefresh
import com.example.studylink.placeholderColor
import com.example.studylink.subheadText
import com.google.common.io.Files.append
var inputText = mutableStateOf("")
@Composable
fun ForumScreen(
    navController: NavHostController,
    forumViewModel: ForumViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val forumUiState by forumViewModel.uiState.collectAsState()

    //First Load
    var initialLoadFinished by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        // Perform initial data load only once
        if (!initialLoadFinished) {
            forumViewModel.getAllForum()
            initialLoadFinished = true
        }
        Log.d("LE_FORUMSCREEN", "Launched Effect Forum Screen Dijalankan")
    }

    val changeIsLoading: () -> Unit = {
        initialLoadFinished = false
    }

    Scaffold(
        modifier = Modifier
            .background(background),
        containerColor = background,
        contentColor = background,
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("${QNA.route}/create")}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { paddingValue ->
        Column {
            ForumProfileTopNavbar()
            ForumSplash()
            SearchBarForum()
            if(inputText.value == ""){
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValue)
                ) {
                    // Display data forum
                    itemsIndexed(forumUiState.forumList) { index, forum ->
                        ForumCard(
                            title = forum.title,
                            author = forum.authorId,
                            timestamp = {
                                forum.timestamp?.let { forumViewModel.getTimeFromNow(it.toDate()) }
                                    ?: ""
                            },
                            text = forum.text,
                            tags = forum.tags.toSet(),
                            voteClick = { forumViewModel.voteForum(index, currUser.value.email) } ,
                            onClick = {
                                forumViewModel.goForumDetail(
                                    forumDocID = forum.documentId,
                                    navController = navController
                                )
                            },
                            upvote = forum.upvote.size,
                            isVoted = forumViewModel.isVoteForumByUserId(index, currUser.value.email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 3.dp)
                        )
                    }
                }
            }else{
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValue)
                ) {
                    // Display data forum
                    itemsIndexed(forumUiState.forumList) { index, forum ->
                        if(forum.title.lowercase().contains(inputText.value.lowercase())){
                            ForumCard(
                                title = forum.title,
                                author = forum.authorId,
                                timestamp = {
                                    forum.timestamp?.let { forumViewModel.getTimeFromNow(it.toDate()) }
                                        ?: ""
                                },
                                text = forum.text,
                                tags = forum.tags.toSet(),
                                voteClick = { forumViewModel.voteForum(index, currUser.value.email) } ,
                                onClick = {
                                    forumViewModel.goForumDetail(
                                        forumDocID = forum.documentId,
                                        navController = navController
                                    )
                                },
                                upvote = forum.upvote.size,
                                isVoted = forumViewModel.isVoteForumByUserId(index, currUser.value.email),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 3.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ForumProfileTopNavbar(
    modifier: Modifier = Modifier
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
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Forum",
                    color = headText,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
                Text(text = inRefresh.value.toString(), color = Color.Transparent)
            }
        }
    }
}

@Composable
fun ForumSplash(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = defaultColor)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.forumsplash),
                contentDescription = "group vector art",
                modifier = Modifier
                    .requiredWidth(width = 225.dp)
                    .requiredHeight(height = 176.dp)
            )
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .offset(x = (-13).dp, y = 0.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = headText,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold)
                        ) {append("Ask")}
                        withStyle(style = SpanStyle(
                            color = headText,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Normal)
                        ) {append(" Your")}},
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = "Question Here",
                    color = headText,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SearchBarForum(
//    inputText: () -> Unit,
    modifier: Modifier = Modifier) {
    var inputText = rememberSaveable { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 42.dp)
            .background(color = defaultColor)
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .wrapContentSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            CustomTextField(
                value = mutableStateOf(inputText.value),
                onValueChange = {
                    inputText.value = it
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        null,
                        tint = placeholderColor.copy(alpha = 0.3f)
                    )
                },
                trailingIcon = null,
                modifier = Modifier
                    .background(
                        Color(0xffededed),
                        RoundedCornerShape(percent = 100)
                    )
                    .clip(shape = RoundedCornerShape(16.dp))
                    .height(30.dp)
                    .fillMaxWidth(),
                placeholderText = "Search Forum",
                useClear = true,
                imeAction = ImeAction.Search
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumCard(
    title: String,
    author: String,
    timestamp: () -> String,
    onClick: () -> Unit,
    voteClick: () -> Unit,
    text: String,
    tags: Set<String>,
    upvote: Int,
    isVoted: Boolean,
    modifier: Modifier = Modifier ){

    val orderedTags = tags.sorted()

    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(cardsColor),
        onClick = { onClick() }
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = headText
            )

            //Date
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = timestamp(), color = subheadText)
                Row(

                ) {
//                    Text(text = "@")
                    Text(text = author, color = subheadText)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tags
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(bottom = 5.dp)
            ){
                items(orderedTags) { tag ->
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

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = subheadText
            )

            // Dashline
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            Canvas(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)) {

                drawLine(
                    color = Color.Red,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    pathEffect = pathEffect
                )
            }
            // Vote

            VoteButton(
                upvote = upvote,
                voteClick = voteClick,
                isVoted = isVoted
                )
        }
    }
}

//@Composable
//fun VoteButton(upvote: Int, voteClick: () -> Unit, isVoted: Boolean){
//    Row(
//        horizontalArrangement = Arrangement.End,
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        Text(upvote.toString(), Modifier.padding(end = 10.dp), color = headText)
//        Button(
//            onClick = voteClick,
//            shape = RoundedCornerShape(20.dp),
//            colors = ButtonDefaults.buttonColors(groupButtonColor),
//        ) {
//            Text(text = "Rounded")
//        }
//    }
//}

@Composable
fun VoteButton(upvote: Int, voteClick: () -> Unit, isVoted: Boolean) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(upvote.toString(), Modifier.padding(end = 10.dp), color = headText)
        IconButton(
            onClick = voteClick,
            modifier = Modifier.size(48.dp)
        ) {
            val icon = if (isVoted) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp
            val tint = if (isVoted) Color(44, 145, 72, 255) else headText
            Icon(icon, contentDescription = "Upvote", tint = tint)
        }
    }
}


//
@Preview
@Composable
fun ForumCardPreview(){
    Column() {
        ForumCard(
            title = "About Palindrome Algorithm",
            timestamp = {"19 August 2020 19:20"},
            author = "James Cameron",
            text = "I still confused as hell about this programming algorithm I still confused as hell about this programming algorithmI still confused as hell about this programming algorithmI still confused as hell about this programming algorithmI still confused as hell about this programming algorithm",
            tags = setOf<String>("C", "Algorithm","Computer Science"),
            onClick = {},
            isVoted = true,
            upvote = 1,
            voteClick = {}
            ,
            modifier = Modifier
                .fillMaxWidth())
        ForumCard(
            title = "About Palindrome Algorithm",
            timestamp = {"19 August 2020 19:20"},
            author = "James Cameron",
            text = "I still confused as hell about this programming algorithm I still confused as hell about this programming algorithmI still confused as hell about this programming algorithmI still confused as hell about this programming algorithmI still confused as hell about this programming algorithm",
            tags = setOf<String>("C", "Algorithm","Computer Science"),
            onClick = {},
            isVoted = false,
            upvote = 21,
            voteClick = {}
            ,
            modifier = Modifier
                .fillMaxWidth())
    }

}

//@Preview
//@Composable
//fun VoteButtonPreview() {
//    VoteButton()
//}