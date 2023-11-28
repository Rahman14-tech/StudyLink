package com.example.studylink.forum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun ForumDetailScreen(
    forumId: String,
    forumViewModel: ForumViewModel = viewModel(),
) {
    val forumUiState by forumViewModel.uiState.collectAsState()
    val forum: ForumModel = forumUiState.activeForum!!

    LaunchedEffect(true) {
        forumViewModel.getForumDetail(forumId)
    }

    // EXPERIMENT
    Text("forumId")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display title
        Text(
            text = forum.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display author
        Text(
            text = "Author: ${forum.authorId}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display timestamp
        forum.timestamp?.let { timestamp ->
            val formattedTimestamp = /* Format timestamp as needed */
                Text(
                    text = "Timestamp: $timestamp",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
        }

        // Display text content
        Text(
            text = forum.text,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display tags
        if (forum.tags.isNotEmpty()) {
            Text(
                text = "Tags: ${forum.tags.joinToString()}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Display upvote count
        Text(
            text = "Upvotes: ${forum.upvote}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}


@Composable
fun CommentBlock() {

}

@Composable
fun FormComponent() {

}