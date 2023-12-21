package com.example.studylink

import VideoPlayerScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MediaViewer(navController: NavHostController,Id:String, MediaUri: String, isGroup: Boolean){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        if(!isGroup){
            TextButton(onClick = { navController.navigate(TheChatS.route+"/${Id}") }, modifier = Modifier.padding(bottom = 20.dp)) {
                Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = "Icon Back", tint = Color.White)
            }
        }else{
            println("YTNKTS ${GroupChats.route+"/${Id}"}")
            TextButton(onClick = { navController.navigate(GroupChats.route+"/${Id}") }, modifier = Modifier.padding(bottom = 20.dp)) {
                Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = "Icon Back", tint = Color.White)
            }
        }
        Column(modifier = Modifier
            .fillMaxSize(),verticalArrangement = Arrangement.Center) {
            VideoPlayerScreen(navController = navController, ChatId = Id, mediaUrl = MediaUri, hideFullscreen = true, isGroup = isGroup)
        }
    }
}