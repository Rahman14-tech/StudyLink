package com.example.studylink

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun Header (headerId: String) {
    when (headerId) {
        "dashboard" -> {
            dashboard()
        }
        "profile" -> {

        }
        "YourChatScreen" -> {
            chatheader()
        }
        "groupchat" -> {

        }
        "qna" -> {

        }
    }

}
@Composable
fun chatheader(){
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = defaultColor)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.1.dp)
            .background(color = defaultColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .requiredHeight(60.dp)
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Your Chats",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = headText,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
                Row (
                    modifier = Modifier
                        .padding(end = 5.dp, top = 5.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = {
                            showYourChatSearch.value = !showYourChatSearch.value
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "",
                            tint = headText,
                            modifier = Modifier
                                .size(26.dp)
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = defaultColor),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = { selectedPeerChats.value = true
                    searchYourChat.value = "" }) {
                    Text(text = "Personal", fontSize = 17.sp, fontWeight = FontWeight.Medium, color = headText)
                }
                if(selectedPeerChats.value){
                    Divider(modifier = Modifier.width(95.dp), color = Color(0xFFFFC600), thickness = 2.dp)
                } else {
                    Divider(modifier = Modifier.width(95.dp), color = Color.Transparent, thickness = 2.dp)
                }
            }
            Column(
                modifier = Modifier
                    .background(color = Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = { selectedPeerChats.value = false
                    searchYourChat.value = "" }) {
                    Text(text = "Group", fontSize = 17.sp, fontWeight = FontWeight.Medium, color = headText)
                }
                if(!selectedPeerChats.value){
                    Divider(modifier = Modifier.width(80.dp), color = Color(0xFFFFC600), thickness = 2.dp)
                } else {
                    Divider(modifier = Modifier.width(80.dp), color = Color.Transparent, thickness = 2.dp)
                }
            }

        }
    }
}

@Composable
fun dashboard(){
    val brush1 = Brush.horizontalGradient(listOf(Color(0xFF2C8DFF), Color(0xFF006DEC)))
    val brush2 = Brush.horizontalGradient(listOf(Color(0xFFFFC600), Color(0xFFF8C105)))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(defaultColor)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.1.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                ) {
                    Text(text = "Study", modifier = Modifier.textBrush(brush1), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "Link", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.textBrush(brush2))
                }
                IconButton(
                    onClick = {
                        if( selectedPeerDashboard.value == true){
                            showDashfilterPersonal.value = true
                        }else{
                            showDashGroupfilter.value = true
                        }

                    },
                    modifier = Modifier
                        .padding(end = 15.dp, top = 7.dp)
                        .align(alignment = Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filtericon),
                        contentDescription = "",
                        tint = headText,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(onClick = { selectedPeerDashboard.value = true }) {
                        Text(text = "Personal", fontSize = 17.sp, fontWeight = FontWeight.Medium, color = headText)
                    }
                    if(selectedPeerDashboard.value){
                        Divider(modifier = Modifier.width(95.dp), color = Color(0xFFFFC600), thickness = 2.dp)
                    } else {
                        Divider(modifier = Modifier.width(95.dp), color = Color.Transparent, thickness = 2.dp)
                    }
                }
                Column(
                    modifier = Modifier
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(onClick = { selectedPeerDashboard.value = false }) {
                        Text(text = "Group", fontSize = 17.sp, fontWeight = FontWeight.Medium, color = headText)
                    }
                    if(!selectedPeerDashboard.value){
                        Divider(modifier = Modifier.width(80.dp), color = Color(0xFFFFC600), thickness = 2.dp)
                    } else {
                        Divider(modifier = Modifier.width(80.dp), color = Color.Transparent, thickness = 2.dp)
                    }
                }
            }
        }
    }
}