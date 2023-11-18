package com.example.studylink

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavHostController){
    Scaffold(bottomBar = {BottomContent(navController = navController)}) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxWidth()){
            NavHost(navController = navController, startDestination = Dashboard.route){
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
                composable(QNA.route){
                    Forum(navController = navController)
                }
                composable(TheChatS.route+"/{${TheChatS.ChatId}}", arguments = listOf(navArgument(TheChatS.ChatId){type = NavType.StringType})){ currIt ->
                    val id = requireNotNull(currIt.arguments?.getString(TheChatS.ChatId))
                    ChatSystem(navController = navController,id)
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
        TheChatS.route+"/{${TheChatS.ChatId}}"-> bottomBarState.value = false
        else -> bottomBarState.value = true

    }
    var destinationList = listOf(QNA,Dashboard, YourChats)
    AnimatedVisibility(visible = bottomBarState.value) {
        Card( modifier = Modifier
            .fillMaxHeight(0.1f)
            .background(color = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(25.dp)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))) {
                destinationList.forEachIndexed{index, destination ->
                    NavigationBarItem(label = {
                        if(navBackStackEntry?.destination?.route == destination.route){
                            Text(
                                text = destination.title, color = Color.Black
                            )
                        }else{
                            Text(
                                text = destination.title, color = Color.Gray
                            )
                        }
                        },selected = navBackStackEntry?.destination?.route == destination.route , onClick = {
                        navController.navigate(destination.route){popUpTo(destination.route) {
                        inclusive = true
                    }} }, icon = { if(navBackStackEntry?.destination?.route == destination.route){
                        Icon(painter = painterResource(id = destination.icon), contentDescription = "",
                            tint = Color.Black
                            , modifier = Modifier.fillMaxSize(0.33f)) }else{
                        Icon(painter = painterResource(id = destination.icon), contentDescription = "",
                            tint = Color.Gray
                            , modifier = Modifier.fillMaxSize(0.33f))
                        }
                    })
                }
                NavigationBarItem(selected = navBackStackEntry?.destination?.route == "Setting" , onClick = {
                    navController.navigate(Setting.route){popUpTo(Setting.route) {
                    inclusive = true
                }} }, icon = { Icon(painter = painterResource(
                    id = Setting.icon
                ), contentDescription = "", tint = Color.Unspecified, modifier = Modifier.fillMaxSize(0.55f)) })
            }
        }

    }
}
