package com.example.studylink

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {

    val isEditingBio = remember { mutableStateOf(false) }

    val editedBio = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
            topBar = {
                TopAppBar(
                        colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.inverseSurface,
                        ),
                        title = {
                            Text("Your Profile", fontWeight = FontWeight.Bold)
                        },
                        actions = {
                            IconButton(
                                    onClick = {
                                        isEditingBio.value = !isEditingBio.value
                                    }
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Profile"
                                )
                            }
                        }
                )
            }
    ) { contentPadding ->
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding) // Add padding to the entire profile content
        ) {
            if (currUser != null) {
                Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFF1F1F1)).padding(bottom = 10.dp)) {

                    Row(modifier = Modifier.padding(top = 20.dp, start = 20.dp)) {
                        val painter = rememberAsyncImagePainter(model = currUser.value.imageURL)
                        Image(
                                painter = painter,
                                contentDescription = "User Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                        )
                        Column {
                            Row() {
                                Text(
                                        text = currUser.value.fullName,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                )
                            }
                            Text(text = "Online", fontSize = 20.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                    Text(text = "Account", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Column {
                        Row() {
                            Text(text = currUser.value.fullName, fontSize = 20.sp)
                        }
                        Text(text = "\nName", fontSize = 20.sp)
                        Divider(color = Color.Black, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(15.dp))

                        Row {
                            Text(text = currUser.value.email, fontSize = 20.sp)
                        }
                        Text(text = "\nEmail", fontSize = 20.sp)
                        Divider(color = Color.Black, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(15.dp))

                        Row {
                            if (isEditingBio.value) {
                                Column(
                                        modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                                ) {
                                    OutlinedTextField(
                                            value = editedBio.value,
                                            onValueChange = {
                                                editedBio.value = it
                                            },
                                            label = { Text("Edit Bio") },
                                            modifier = Modifier
                                                    .fillMaxWidth()
                                    )
                                }
                            } else {
                                Text(text = "I'm strong at ${currUser.value.strongAt}, and I want to study ${currUser.value.wantStudy}", fontSize = 20.sp)
                            }

                            // Please fix this shit, it's not appearing on the app
                            IconButton(
                                    onClick = {
                                        isEditingBio.value = false
                                    },
                                    modifier = Modifier.padding(start = 16.dp)
                                            .size(20.dp)
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Save,
                                        contentDescription = "Save",
                                        modifier = Modifier.size(20.dp)
                                )
                            }

                        }

                        Text(text = "\nBio", fontSize = 20.sp)
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box (modifier = Modifier.fillMaxWidth() .height(10.dp)
                        .background(Color.LightGray)) {

                }

                Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                    Text(text = "Settings", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                }

            } else {
                Text(text = "User not found")
            }
        }
    }
}


