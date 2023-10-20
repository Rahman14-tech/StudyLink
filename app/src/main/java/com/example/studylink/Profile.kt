package com.example.studylink

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    // Get the email from currScreen
    val email = currScreen.value.text

    // Find the user's profile based on the email
    val userProfile = Realusers.find { it.email == email }
            if (userProfile != null) {
                // Display the user's profile data
                Column(
                    modifier = Modifier
                        .padding(16.dp) // Add appropriate padding
                ) {
                    Text(text = "Full Name: ${userProfile.fullName}", modifier = Modifier.padding(4.dp))
                    // Add more Text components to display other profile information
                    Text(text = "Email: ${userProfile.email}", modifier = Modifier.padding(4.dp))
                    // Add more profile information as needed
                }
            } else {
                Text(text = "User not found")
            }
}



