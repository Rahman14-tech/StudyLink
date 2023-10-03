package com.example.studylink

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(navController: NavHostController){
    Scaffold(bottomBar = {BottomContent(navController = navController)}) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxWidth()){
            NavHost(navController = navController, startDestination = Login.route){
                composable(Login.route){
                    LoginScreen(navController = navController)
                }
                composable(Register.route){
                    RegisterScreen(navController = navController)
                }
                composable(Dashboard.route){
                    DashboardScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomContent(navController: NavHostController){
    val IsLogin = rememberSaveable {
        (mutableStateOf(false))
    }
    AnimatedVisibility(visible = IsLogin.value) {
        NavigationBar() {
//        NavigationBarItem(selected = , onClick = { /*TODO*/ }, icon = { /*TODO*/ })
        }
    }
}
