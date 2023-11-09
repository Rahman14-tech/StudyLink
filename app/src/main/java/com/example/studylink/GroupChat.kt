package com.example.studylink

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(){

}

@Composable
fun GroupChatCard(name: String, subject: String, modifier: Modifier = Modifier ){
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row (
            Modifier
                .padding(5.dp)
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
        ){
            Card (
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC600)),

            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.default_image_group),
//                    contentDescription = "GroupChat",
//                    modifier = Modifier
//                        .padding(top = 6.dp)
//                        .align(Alignment.CenterHorizontally)
//                        .size(80.dp)
//
//
//                )
            }
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(5.dp)
            ) {
                // Title
                Text(
                    text = name,
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold
//                    )
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                //Subject
                Text(text = subject)
            }
        }


    }
}

@Preview
@Composable
fun PreviewGroupChatCard(){
    GroupChatCard(
        name = "Naufal, Humam",
        subject = "Math | Physics",
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth())
}