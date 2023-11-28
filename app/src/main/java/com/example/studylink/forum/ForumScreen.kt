package com.example.studylink.forum

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studylink.BottomBar
import com.example.studylink.BottomContent

@Composable
fun ForumScreen(
    navController: NavHostController,
    forumViewModel: ForumViewModel = viewModel(),
    modifier: Modifier = Modifier) {
    val forumUiState by forumViewModel.uiState.collectAsState()

    //First Load
    var initialLoadFinished by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        // Perform initial data load only once
        if (!initialLoadFinished) {
            forumViewModel.getAllForum()
            initialLoadFinished = true
        }
    }

    Scaffold () {paddingValue ->
        Text("This is FORUM")
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValue)
        ) {
            // Display data forum
            items(forumUiState.forumList) {forum ->
                ForumCard(
                    title = forum.title,
                    author = forum.authorId,
                    timestamp = { forum.timestamp?.let { forumViewModel.getTimeFromNow(it.toDate()) } ?: ""},
                    text = forum.text,
                    tags = forum.tags.toSet(),
                    onClick = { forumViewModel.goForumDetail(
                        forumDocID = forum.documentId,
                        navController = navController
                    )},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
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
    text: String,
    tags: Set<String>,
    modifier: Modifier = Modifier ){

    val orderedTags = tags.sorted()

    Card (
        modifier = modifier,
//        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                )

                //Date
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = timestamp())
                    Row(

                    ) {
                        Text(text = "@")
                        Text(text = author)
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
                            color = Color.Black,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Subtitle
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    )

                // Vote


            }
        }
    }

@Composable
fun VoteButton(){
    Row() {

    }

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Text(text = "Rounded")
    }
}
//
//@Preview
//@Composable
//fun ForumCardPreview(){
//    ForumCard(
//        title = "About Palindrome Algorithm",
//        timestamp = {"19 August 2020 19:20"},
//        author = "James Cameron",
//        text = "I still confused as hell about this programming algorithm I still confused as hell about this programming algorithmI still confused as hell about this programming algorithmI still confused as hell about this programming algorithmI still confused as hell about this programming algorithm",
//        tags = setOf<String>("C", "Algorithm","Computer Science"),
//        modifier = Modifier
//            .fillMaxWidth())
//}

@Preview
@Composable
fun VoteButtonPreview() {
    VoteButton()
}