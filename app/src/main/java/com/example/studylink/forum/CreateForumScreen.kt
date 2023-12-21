package com.example.studylink.forum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studylink.currUser
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CreateForumScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = tags,
            onValueChange = { tags = it },
            label = { Text("Tags (comma-separated)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Button(
            onClick = {
//                var documentId: String = "",
//                val authorId: String = "",
//                val title: String = "",
//                val timestamp: Timestamp? = null,
//                val text: String = "",
//                val upvote: List<String> = listOf(),
//                val tags: List<String> = listOf(),

                val forumModel = ForumModel(
                    authorId = currUser.value.email ?: "",
                    title = title,
                    text = text,
                    tags = tags.split(","),
                    upvote = listOf(),
                    timestamp = Timestamp.now()
                )
                addForumToFirebase(forumModel)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Create Forum")
        }
    }
}

private fun addForumToFirebase(forumModel: ForumModel) {
    val forumRef = FirebaseFirestore.getInstance().collection("ForumTest")
    forumRef
        .add(forumModel)
        .addOnSuccessListener { documentReference ->
            // On success
        }
        .addOnFailureListener { e ->
            // On failure
        }
}
