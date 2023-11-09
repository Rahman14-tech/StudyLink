package com.example.studylink

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavHostController){
    Scaffold(bottomBar = {BottomContent(navController = navController)}) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxWidth()){
            NavHost(navController = navController, startDestination = YourChats.route){
                composable(Login.route){
                    LoginScreen(navController = navController)
                }
                composable(Register.route){
                    RegisterScreen(navController = navController)
                }
                composable(Dashboard.route){
                    DashboardScreen(navController = navController)
                }
                composable(YourChats.route){
                    YourChatScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomContent(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    when (navBackStackEntry?.destination?.route) {
        "Login"->bottomBarState.value = false
        "Register"->bottomBarState.value = false
        else -> bottomBarState.value = true

    }
    AnimatedVisibility(visible = bottomBarState.value) {
        Card( modifier = Modifier
            .fillMaxHeight(0.07f)
            .background(color = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
            NavigationBar() {
                NavigationBarItem(selected = false , onClick = { /*TODO*/ }, icon = { Icon(imageVector = Icons.Outlined.QuestionAnswer, contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp)) })
                NavigationBarItem(selected = false , onClick = { /*TODO*/ }, icon = { Icon(imageVector = Icons.Outlined.Chat, contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp)) })
                NavigationBarItem(selected = true , onClick = { /*TODO*/ }, icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp)) })
                NavigationBarItem(selected = false , onClick = { /*TODO*/ }, icon = { Icon(imageVector = Icons.Outlined.Group, contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp)) })
                NavigationBarItem(selected = false , onClick = { /*TODO*/ }, icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = "", tint = Color.Black, modifier = Modifier.size(30.dp)) })

            }
        }

    }
}
