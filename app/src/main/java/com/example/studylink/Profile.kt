package com.example.studylink

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    // Get the email from currScreen
    val email = currScreen.value.text

    // Find the user's profile based on the email
    val userProfile = Realusers.find { it.email == email }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Your Profile")
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding) // Add padding to the entire profile content
        ) {
            if (userProfile != null) {
                Row(modifier = Modifier.padding(top = 20.dp, start = 20.dp)) {
                    Image(painter = painterResource(id = R.drawable.facecom), contentDescription = "Temp Image")
                    Column {
                        Row() {
                            Text(text = userProfile.fullName, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Online", fontSize = 20.sp)
                    }
                }
            } else {
                Text(text = "User not found")
            }
        }
    }
}
