package com.example.studylink

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun GeneratedCode(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 80.dp)
    ) {

    }
}

@Preview(widthDp = 360, heightDp = 80)
@Composable
private fun GeneratedCodePreview() {
    GeneratedCode(Modifier)
}
@Composable
fun Header (currScreen: String) {
    val brush1 = Brush.horizontalGradient(listOf(Color(0xFF2C8DFF), Color(0xFF006DEC)))
    val brush2 = Brush.horizontalGradient(listOf(Color(0xFFF5ED37), Color(0xFFCCC51B)))
    if(currScreen == "Dashboard"){
        Card(modifier = Modifier.fillMaxWidth().background(color = Color.White), shape = RectangleShape, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.1.dp)
                .background(color = Color.White)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Row {

                    }
                    Row(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Study", modifier = Modifier.textBrush(brush1), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = "Link", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.textBrush(brush2))
                    }
                    Row () {
                        IconButton(
                            onClick = {
//                        Toast.makeText(contextForToast, "Click!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Outlined.Tune, contentDescription = "", tint = Color.Black, modifier = Modifier.size(38.dp).padding(end = 20.dp, top = 10.dp))
                        }

                    }
                }
            }
        }
    }else if(currScreen == "YourChatScreen"){
        Card(modifier = Modifier.fillMaxWidth().background(color = Color.White), shape = RectangleShape, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.1.dp)
                .background(color = Color.White)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Your ", modifier = Modifier.textBrush(brush1), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = "Chats", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.textBrush(brush2))
                    }
                    Row (modifier = Modifier.padding(end = 10.dp, top = 5.dp)) {
                        IconButton(
                            onClick = {
//                        Toast.makeText(contextForToast, "Click!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = "", tint = Color.Black, modifier = Modifier.size(33.dp),)
                        }

                    }
                }
            }
        }
    }

}
