package com.example.studylink.forum

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studylink.QNA
import com.example.studylink.background
import com.example.studylink.currUser
import com.example.studylink.headText
import com.example.studylink.inRefresh
import com.example.studylink.ui.theme.StudyLinkTheme
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateForumScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var appliedTags by remember { mutableStateOf(mutableSetOf<String>()) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val changeIsLoadingCallback = navBackStackEntry?.arguments?.getString("changeIsLoading") as? () -> Unit


    Scaffold (
        modifier = Modifier
            .background(background),
        containerColor = background,
        contentColor = background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create New Forum",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            )
        }
    ){ paddingValue ->
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column (
                modifier = Modifier
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Text") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = false
                )
                OutlinedTextField(
                    value = tags,
                    onValueChange = {
                        if (it.all { char -> char.isLetter() || char.isWhitespace() || char == ',' }) {
                            tags = it
                        }
                    },
                    label = { Text("Tags (comma-separated)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )

                // Aplikasikan tags
                if (!tags.isNullOrEmpty()) {
                    Button(
                        onClick = {
                            // Tambahkan ke dalam list appliedTags
                            appliedTags.addAll(tags.split(","))
                            // hapus value tags
                            tags = ""
                        }
                    ) {
                        Text("Apply tag")
                    }
                } else {
                    OutlinedButton(
                        onClick = { /* Optional click action */ },
                        enabled = false // To make the button disabled
                    ) {
                        Text("Apply tag")
                    }
                }

                // Menampilkan list tags yang telah di apply
                // ==========================================
                val orderedTags = appliedTags.sorted()
                // Tags
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                ){
                    itemsIndexed(orderedTags) { index, tag ->
                        val coroutineScope = rememberCoroutineScope()
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ){
                            Row {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Default.ChevronRight) ,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp) // Set the size of the icon
                                        .padding(4.dp), // Add padding if necessary
                                    tint = Color.Black // Set the color of the icon
                                )
                                Text(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(color = Color(0xFFFFC600))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                        .height(20.dp),
                                    text = tag,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = headText,
                                )
                            }

                            // Delete applied tags
                            IconButton(onClick = {
                                // Delete applied tags
                                val deleteTag = orderedTags[index]
                                appliedTags.remove(deleteTag)
                                inRefresh.value++
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.Red // Add tint color if needed
                                )
                            }
                        }

                    }

                }
                Text(text = inRefresh.value.toString(), color = Color.Transparent )
            }

            Column (
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        val forumModel = ForumModelnoDocId(
                            authorId = currUser.value.email ?: "",
                            title = title,
                            text = text,
                            tags = appliedTags.toList(),
                            upvote = listOf(),
                            timestamp = Timestamp.now()
                        )
                        changeIsLoadingCallback?.invoke()
                        addForumToFirebase(
                            forumModel = forumModel,
                            context = context,
                            navController = navController)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Create Forum")
                }
            }
        }
    }





}

@Preview
@Composable
fun PreviewCreateForum(){
    StudyLinkTheme {
        CreateForumScreen(navController =  rememberNavController() )
    }
}

private fun addForumToFirebase(forumModel: ForumModelnoDocId, context: Context, navController: NavController) {
    val forumRef = FirebaseFirestore.getInstance().collection("ForumTest3")
    forumRef
        .add(forumModel)
        .addOnSuccessListener { documentReference ->

            Toast.makeText(context,"Forum Berhasil dibuat!", Toast.LENGTH_LONG ).show()
            navController.popBackStack()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context,"Forum Gagal dibuat!", Toast.LENGTH_LONG ).show()
        }
}
