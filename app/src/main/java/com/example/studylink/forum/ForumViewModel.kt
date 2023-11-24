package com.example.studylink.forum

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.w3c.dom.Comment
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

//data class ForumModel(
//    val forumId: String,
//    val authorId: String,
//    val title: String,
//    val timestamp: Timestamp,
//    val text: String,
//    val upvote: Int,
//    val tags: List<String>,
//)

data class ForumModel(
    var documentId: String = "",
    val authorId: String = "",
    val title: String = "",
    val timestamp: Timestamp? = null,
    val text: String = "",
    val upvote: Int = 0,
    val tags: List<String> = listOf(),
)

data class CommentModel(
    val text: String,
    val timestamp: Timestamp,
    val upvote: Int,
    val userId: String,
)

data class ForumUiState(
    val forumList: List<ForumModel> = listOf(),
    val documentIdMap: Map<String, ForumModel> = mapOf(),
    val activeForumId: String = "",
    val commentList: List<CommentModel> = listOf(),
)

class ForumViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ForumUiState())
    val uiState: StateFlow<ForumUiState> = _uiState.asStateFlow()

    fun getAllForum() {
        println("GET ALLFORUM STARTED")
        val forumList = mutableListOf<ForumModel>()
        val documentIdMap: MutableMap<String,ForumModel> = mutableMapOf()
        val forumRef = FirebaseFirestore.getInstance().collection("ForumTest")

        Log.d("GETALLFORUM", "GET ALL FORUM STARTED")
        forumRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val forum = document.toObject(ForumModel::class.java)
                forumList.add(forum)
                documentIdMap[document.id] = forum
            }
            _uiState.update { currentState ->
                currentState.copy(
                    forumList = forumList,
                    documentIdMap = documentIdMap
                )
            }
            Log.d("TEST", forumList.toString())
            println("THIS IS THE FORUM LIST:: $forumList")
            documentIdMap.forEach { (key, value) ->
                Log.d("TESTMAP", "Key: $key, Value: $value")
            }
        }.addOnFailureListener { exception ->
            // Handle the failure
            Log.e("ForumViewModel", "Error getting forums", exception)
        }
    }


    fun getTimeFromNow(timestamp: Date): String {
        val now = Date()

        val diffInMillis = now.time - timestamp.time
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)

        return when {
            seconds < 60 -> "just now"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            else -> {
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dateFormatter.format(timestamp)
            }
        }
    }
}