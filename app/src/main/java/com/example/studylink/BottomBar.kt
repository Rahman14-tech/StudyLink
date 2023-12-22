package com.example.studylink

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.studylink.forum.CreateForumScreen
import com.example.studylink.forum.ForumDetailScreen
import com.example.studylink.forum.ForumScreen
import com.example.studylink.forum.ForumViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavHostController) {
    val currentUser = auth.currentUser
    Scaffold(bottomBar = {BottomContent(navController = navController)}) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxWidth()
        ) {
            var beginningRoute = Login.route
            if(currentUser != null) {
                val jetztUser = Realusers.firstOrNull { it.email == currentUser.email }
                if(jetztUser != null){
                    currUser.value = jetztUser
                    beginningRoute = Dashboard.route
                }
            }
            NavHost(navController = navController, startDestination = beginningRoute){
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
                    ForumScreen(navController = navController)
                }
                composable(route = "${QNA.route}/{detail}",
                    arguments = listOf(navArgument("detail"){
                        type = NavType.StringType
                    })){backStackEntry ->

                    ForumDetailScreen(forumId = requireNotNull(backStackEntry.arguments?.getString("detail")), navController = navController)
                }
                composable("${QNA.route}/create"){
                    CreateForumScreen(navController = navController)
                }

                composable(TheChatS.route+"/{${TheChatS.ChatId}}", arguments = listOf(navArgument(TheChatS.ChatId){type = NavType.StringType})){ currIt ->
                    val id = requireNotNull(currIt.arguments?.getString(TheChatS.ChatId))
                    ChatSystem(navController = navController,id)
                }
                composable(MediaViewer.route+"/{${MediaViewer.ChatId}}"+"/{${MediaViewer.MediaUri}}"+"/{${MediaViewer.isGroup}}", arguments = listOf(
                    navArgument(MediaViewer.ChatId){type = NavType.StringType}, navArgument(MediaViewer.MediaUri){type = NavType.StringType}, navArgument(MediaViewer.isGroup){type = NavType.BoolType}
                )){currIt ->
                    val id = requireNotNull(currIt.arguments?.getString(MediaViewer.ChatId))
                    val mediaUri = requireNotNull(currIt.arguments?.getString(MediaViewer.MediaUri))
                    val isGroup = requireNotNull(currIt.arguments?. getBoolean(MediaViewer.isGroup))
                    MediaViewer(navController = navController, Id = id, MediaUri = mediaUri, isGroup = isGroup)
                }
                composable(Setting.route){
                    ProfileScreen(navController = navController)
                }
                composable(GroupChats.route+"/{${GroupChats.GroupChatId}}", arguments = listOf(
                    navArgument(GroupChats.GroupChatId){type = NavType.StringType}
                )){currIt2->
                    val id = requireNotNull(currIt2.arguments?.getString(GroupChats.GroupChatId))
                    println("ANJAYTHE ${id}")
                    GroupChatSystem(navController = navController, GroupChatId = id)
                }
            }
        }
    }
}

@Composable
fun BottomContent(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    when (navBackStackEntry?.destination?.route) {
        "Login"->bottomBarState.value = false
        "Register"->bottomBarState.value = false
        GroupChats.route+"/{${GroupChats.GroupChatId}}" ->bottomBarState.value = false
        TheChatS.route+"/{${TheChatS.ChatId}}"-> bottomBarState.value = false
        MediaViewer.route+"/{${MediaViewer.ChatId}}"+"/{${MediaViewer.MediaUri}}"+"/{${MediaViewer.isGroup}}" -> false
        else -> bottomBarState.value = true

    }
    var destinationList = listOf(QNA,Dashboard, YourChats)
    AnimatedVisibility(visible = bottomBarState.value) {
        Text(text = inRefresh.value.toString(), color = Color.Transparent)
        Card(
            modifier = Modifier
                .fillMaxHeight(0.1f),
            colors = CardDefaults.cardColors(containerColor = defaultColor),
            shape = RectangleShape
        ) {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth(),
                containerColor = defaultColor
            ) {
                destinationList.forEachIndexed{ index, destination ->
                    NavigationBarItem(
                        label = {
                            if(navBackStackEntry?.destination?.route == destination.route){
                                Text(
                                    text = destination.title, color = headText
                                )
                            } else {
                                Text(
                                    text = destination.title, color = placeholderColor
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(Color.Transparent),
                        selected = navBackStackEntry?.destination?.route == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(destination.route) { inclusive = true }
                            }
                        },
                        icon = {
                            if(navBackStackEntry?.destination?.route == destination.route) {
                                Icon(
                                    painter = painterResource(id = destination.icon),
                                    contentDescription = "",
                                    tint = headText,
                                    modifier = Modifier.fillMaxSize(0.33f)
                                )
                            } else {
                                Icon(painter = painterResource(id = destination.icon),
                                    contentDescription = "",
                                    tint = placeholderColor,
                                    modifier = Modifier.fillMaxSize(0.33f)
                                )
                            }
                        }
                    )
                }
                NavigationBarItem(
                    selected = navBackStackEntry?.destination?.route == "Setting" ,
                    onClick = {
                        navController.navigate(Setting.route) {
                            popUpTo(Setting.route) {inclusive = true }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Transparent),
                    icon = {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .fillMaxSize(0.55f)
                                .padding(0.dp)
                        ) {
                            if(navBackStackEntry?.destination?.route == "Setting") {
                                alphaSettingSelected.value = 1f
                                inRefresh.value++
                            } else {
                                alphaSettingSelected.value = 0.6f
                                inRefresh.value++
                            }
                            Image(
                                painter = rememberAsyncImagePainter(model = currUser.value.imageURL),
                                contentDescription = "User Picture",
                                contentScale = ContentScale.Crop,
                                alpha = alphaSettingSelected.value,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = CircleShape)
                                    .align(alignment = Alignment.Center)
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .clip(CircleShape)
                )
            }
        }

    }
}

@Preview(widthDp = 393, heightDp = 100)
@Composable
fun bottompv() {
    BottomContent(navController = rememberNavController())
}

var inRefresh: MutableState<Int> = mutableStateOf(0)