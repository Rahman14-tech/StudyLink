package com.example.studylink

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun YourChatsCard(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)
        .height(80.dp), shape = RectangleShape, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.1.dp)
            .background(color = Color.White)
        ) {
            Row(modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Card(shape = CircleShape, modifier = Modifier
                        .height(60.dp)
                        .width(50.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC600))) {
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painter = painterResource(id = R.drawable.inorin), contentScale = ContentScale.Crop, contentDescription = "Gambar Wong", modifier = Modifier
                                .size(60.dp)
                                .clip(
                                    CircleShape
                                ))
                        }
                    }
                    Row {
                        Image(painter = painterResource(id = R.drawable.wong), contentDescription = "Gambar Wong", modifier = Modifier.size(30.dp))
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Minase Inori ", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                                Text(text = "15:36", fontWeight = FontWeight.Normal,fontSize = 15.sp,)
                            }
                            Text(text = "This method should be the easiest, so the way is",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 5.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun YourChatScreen(navController: NavHostController){
    Column {
        Header("YourChatScreen")
        LazyColumn(){
            item{

                YourChatsCard()
            }
        }

    }
}