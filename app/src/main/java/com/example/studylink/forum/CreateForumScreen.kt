package com.example.studylink.forum

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studylink.QNA
import com.example.studylink.R
import com.example.studylink.background
import com.example.studylink.currUser
import com.example.studylink.defaultColor
import com.example.studylink.groupButtonColor
import com.example.studylink.headText
import com.example.studylink.inRefresh
import com.example.studylink.subheadText
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
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val changeIsLoadingCallback = navBackStackEntry?.arguments?.getString("changeIsLoading") as? () -> Unit

    Box(
        modifier = Modifier
            .background(color = defaultColor)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ForumCreateTopBar {
                navController.popBackStack()
            }
            Column (
                modifier = Modifier
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    textStyle = TextStyle(
                        color = headText,
                    ),
                    label = { Text(text = "Title", color = headText) },
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
                    textStyle = TextStyle(
                        color = headText,
                    ),
                    label = { Text("Text", color = headText) },
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
                    textStyle = TextStyle(
                        color = headText,
                    ),
                    label = { Text("Tags (comma-separated)", color = headText) },
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
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = subheadText,
                            disabledContentColor = Color.White
                        ),
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
//                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ){
                    itemsIndexed(orderedTags) { index, tag ->
                        val coroutineScope = rememberCoroutineScope()
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.ChevronRight) ,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp) // Set the size of the icon
                                    .padding(4.dp), // Add padding if necessary
                                tint = subheadText // Set the color of the icon
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

                            // Delete applied tags
                            IconButton(
                                onClick = {
                                    // Delete applied tags
                                    val deleteTag = orderedTags[index]
                                    appliedTags.remove(deleteTag)
                                    inRefresh.value++
                                }
                            ) {
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
        }
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
                .height(40.dp)
                .padding(start = 10.dp, end = 10.dp)
                .align(Alignment.BottomCenter)
                .offset(x = 0.dp, y = (-5).dp)
        ) {
            Text(text = "Create Forum", style = TextStyle(fontSize = 17.sp))
        }
    }
}

@Composable
fun ForumCreateTopBar(
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

@Preview
@Composable
fun PreviewCreateForum(){
    StudyLinkTheme {
        CreateForumScreen(navController =  rememberNavController() )
    }
}

private fun addForumToFirebase(forumModel: ForumModelnoDocId, context: Context, navController: NavController) {
    val forumRef = FirebaseFirestore.getInstance().collection(forumCollectionPath)
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
