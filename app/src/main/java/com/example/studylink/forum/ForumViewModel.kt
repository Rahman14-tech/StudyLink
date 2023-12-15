package com.example.studylink.forum

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.studylink.QNA
import com.example.studylink.currUser
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.w3c.dom.Comment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.flow.isActive
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
    val documentId: String = "",
    val text: String = "",
    val timestamp: Timestamp? = null,
    val upvote: Int = 0,
    val userId: String = "",
)

data class ForumUiState(
    val forumList: List<ForumModel> = listOf(),
    val activeForum: ForumModel? = ForumModel(),
    val commentList: List<CommentModel> = listOf(),
)

class ForumViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ForumUiState())
    val uiState: StateFlow<ForumUiState> = _uiState.asStateFlow()

    private val forumCollectionPath = "ForumTest"
    private val commentCollectionPath = "CommentList"
    private val forumRef = FirebaseFirestore.getInstance().collection(forumCollectionPath)

    fun getAllForum() {
        println("GET ALLFORUM STARTED")
        val forumList = mutableListOf<ForumModel>()
        val documentIdMap: MutableMap<String,ForumModel> = mutableMapOf()


        Log.d("GETALLFORUM", "GET ALL FORUM STARTED")
        forumRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val forum = document.toObject(ForumModel::class.java)
                val forumWithID = forum.copy(documentId = document.id)
                forumList.add(forumWithID)
//                documentIdMap[document.id] = forum
            }
            _uiState.update { currentState ->
                currentState.copy(
                    forumList = forumList
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


    fun goForumDetail(navController: NavController, forumDocID: String) {
        // Navigate to Forum Detail
        navController.navigate("${QNA.route}/$forumDocID")
    }

    fun getForumDetail(forumDocID: String) {
        val forumRef = FirebaseFirestore.getInstance().collection(forumCollectionPath).document(forumDocID)
        Log.d("FORUM", "Get forum detail dijalankan dengan id: $forumDocID")
        forumRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Convert document data to ForumModel
                    _uiState.update { currentState ->
                        val newForum: ForumModel? = documentSnapshot.toObject(ForumModel::class.java)
                        newForum?.documentId = forumDocID
                        currentState.copy(
                            activeForum = newForum
                        )
                    }
                    // Use the forumData here (forumData will be of type ForumModel)
                } else {
                    // Document doesn't exist
                    println("Document does not exist")
                }
            }
            .addOnFailureListener { exception ->
                // Handle failures
                println("Error getting documents: $exception")
            }
    }

    fun getForumCommentByForumID(forumDocID: String) {
        forumRef.document(forumDocID).collection(commentCollectionPath)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val newCommentList = mutableListOf<CommentModel>()
                for (document in querySnapshot) {
                    val forumComment = document.toObject(CommentModel::class.java)
                    val forumCommentWithID = forumComment.copy(documentId = document.id)
                    newCommentList.add(forumCommentWithID)
                    Log.d("FORUMCOMMENT", "doc id: $forumDocID, comment text: ${forumCommentWithID.text}")
                }

                // Sort comments by timestamp
                val newCommentListSorted = newCommentList.sortedBy { it.timestamp }

                // Update UI state after sorting
                _uiState.update { currentState ->
                    currentState.copy(
                        commentList = newCommentListSorted
                    )
                }

                // Logging or any other operations related to sorted comments can also be done here
                for (comment in newCommentListSorted) {
                    Log.d("COMMENTLIST", comment.text)
                }
            }
            .addOnFailureListener { exception ->
                // Handle failures
                println("Error getting CommentList documents: $exception")
            }
    }


    fun addForumComment(Input: String) {
        Log.d("ADDFORUM", "adding forum ${uiState.value.activeForum?.documentId}");
        val activeForumDocumentId : String = checkNotNull(uiState.value.activeForum?.documentId)
        val commentValue = hashMapOf(
            "text" to Input,
            "timestamp" to  FieldValue.serverTimestamp(),
            "upvote" to 0,
            "userId" to currUser.value.email
        )
        forumRef.document(activeForumDocumentId).collection(commentCollectionPath).add(
            commentValue
        )
    }




}