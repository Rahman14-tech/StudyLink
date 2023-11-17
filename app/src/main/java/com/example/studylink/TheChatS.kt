package com.example.studylink

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun SendMessage(TheMessage: String, ChatId: String){
    val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
    val currentDate = sdf.format(Date())
    db.collection("Chats").document(ChatId).collection("ChatData").add(hashMapOf(
        "Content" to TheMessage,
        "TheUser" to currUser.value.email,
        "TimeSent" to currentDate
    ))
}

@Composable
fun TopNavbarPersonal(modifier: Modifier = Modifier, navController: NavHostController, ChatId: String) {
    val temnpChatPartner = tempTheChat.first{ it.id == ChatId}
    val tempPartnerEmail = temnpChatPartner.FkUsers.first{ it != currUser.value.email}
    val tempPartnerData = Realusers.first { it.email ==  tempPartnerEmail}
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 60.dp)
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
        ) {
            Row {
                TextButton(
                    onClick = { navController.navigate(Dashboard.route){popUpTo(YourChats.route) {
                        inclusive = true
                    }} },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = modifier
                        .size(20.dp)
                        .align(alignment = Alignment.CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.backbutton),
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .requiredWidth(width = 10.dp)
                            .requiredHeight(height = 18.dp)
                    )
                }
//                Image(
//                    painter = painterResource(id = R.drawable.backbutton),
//                    contentDescription = "Back Button",
//                    modifier = Modifier
//                        .align(alignment = Alignment.CenterVertically)
//                        .requiredWidth(width = 10.dp)
//                        .requiredHeight(height = 18.dp)
//                )
                Spacer(modifier = Modifier.width(25.dp))
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredSize(size = 42.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .requiredSize(size = 42.dp)
                            .clip(shape = CircleShape)
                            .background(color = Color(0xffffc600))
                    )
                    Image(painter = rememberAsyncImagePainter(tempPartnerData.imageURL), contentScale = ContentScale.Crop, contentDescription = "Gambar Wong", modifier = Modifier
                        .size(42.dp)
                        .clip(
                            CircleShape
                        ))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredWidth(width = 146.dp)
                        .requiredHeight(height = 40.dp)
                ) {
                    Text(
                        text = tempPartnerData.fullName,
                        color = Color(0xff202020),
                        style = TextStyle(
                            fontSize = 18.sp)
                    )
                    Text(
                        text = "last seen 15:47",
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontSize = 13.sp)
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.dotmenu),
                contentDescription = "dot menu",
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .requiredSize(size = 20.dp)
            )
        }
    }
}
//
//@Preview(widthDp = 393)
//@Composable
//private fun TopNavbarPreview() {
//    TopNavbar(Modifier)
//}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(modifier: Modifier = Modifier, ChatId: String) {
    var inputText = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics

    val screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    val maxWidthTextField = (screenWidthInDp - 110.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(maxWidthTextField + 40.dp)
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row {
                TextButton(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = modifier
                        .requiredSize(size = 50.dp)
                        .offset(x = 0.dp, y = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .requiredSize(size = 40.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color.DarkGray)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.addicon),
                            contentDescription = null,
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 5.dp, y = 5.dp)
                                .requiredSize(size = 30.dp))
                    }
                }
                TextField(
                    placeholder = {
                        Text(
                            "Type a Message",
                            style = TextStyle(fontSize = 18.sp),
                            color = Color(0xff202020),
                            modifier = Modifier
                                .padding(0.dp)
                        )
                    },
                    value = inputText.value,
                    onValueChange = { inputText.value = it },
                    textStyle = TextStyle(fontSize = 18.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .width(maxWidthTextField - 20.dp)
                        .heightIn(max = 120.dp)
                        .align(alignment = Alignment.Top)
                        .drawWithContent {
                            drawContent()
                        }
                )
            }
        }
        TextButton(
            onClick = {
                SendMessage(TheMessage = inputText.value, ChatId = ChatId)
                inputText.value = ""
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = modifier
                .align(alignment = Alignment.TopEnd)
                .requiredSize(size = 55.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 55.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = Color(0xffffc600))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sendicon),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 16.dp, y = 14.dp)
                        .requiredSize(size = 28.dp))
            }
        }
    }
}


@SuppressLint("NewApi")
@Composable
fun RealTimeClock(): String {
    val currentTimeMillis by rememberUpdatedState(System.currentTimeMillis())
    val currentTime = Instant.ofEpochMilli(currentTimeMillis)

    return DateTimeFormatter.ofPattern("HH:mm").format(currentTime)
}

@Composable
fun LeftChat(modifier: Modifier = Modifier, message : String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var eightyPercentOfScreenWidth = (screenWidthInDp * 0.8f)

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                content = {
                    Text(
                        text = message,
                        color = Color(0xff202020),
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .widthIn(max = eightyPercentOfScreenWidth)
                            .padding(10.dp)
                    )
                },
                modifier = Modifier
                    .wrapContentSize()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .background(color = Color.White)
            )
            Text(
                text = "11:33",
                color = Color.DarkGray,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(start = 4.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
fun RightChat(modifier: Modifier = Modifier, message : String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var eightyPercentOfScreenWidth = (screenWidthInDp * 0.8f)

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 5.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = "11:23",
                color = Color.DarkGray,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(end = 4.dp, bottom = 4.dp)
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xff0066e4)
                ),
                content = {
                    Text(
                        text = message,
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .widthIn(max = eightyPercentOfScreenWidth)
                            .padding(10.dp)
                    )
                },
                modifier = Modifier
                    .wrapContentSize()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .background(color = Color(0xff0066e4))
            )
        }
    }
}

fun GetTheChats(ChatId: String){
    db.collection("Chats").document(ChatId).collection("ChatData").addSnapshotListener{snapshot, e ->
        if (e != null) {
            Log.w(ContentValues.TAG, "Listen failed.", e)
            return@addSnapshotListener
        }
        if(snapshot != null && !snapshot.isEmpty){
            ChatData.clear()
            val list = snapshot.documents
            for(datum in list){
                val c: ChatDataType? = datum.toObject(ChatDataType::class.java)
                if(c!= null){
                    ChatData.add(c)
                    println("Testing Anjay $c")
                }
            }
            ChatData.sortBy { it.TimeSent }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatSystem(navController: NavHostController, ChatId: String) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics

    val screenHeightInDp = with(LocalDensity.current) {
        displayMetrics.heightPixels.dp / density
    }

    val maxHeightChatDisplay = (screenHeightInDp - 68.dp)

    LaunchedEffect(Unit){
        ChatData.clear()
        GetTheChats(ChatId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xfff1f1f1)
            )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .padding(top = 2.dp)
        ) {
            TopNavbarPersonal(navController = navController, ChatId = ChatId)
        }
        Box(
            modifier = Modifier
                .heightIn(max = maxHeightChatDisplay)
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState(initial = 14))
            ) {
                ChatData.forEach {
                    if(it.TheUser != currUser.value.email){
                        LeftChat(Modifier, it.Content)
                    }else{
                        RightChat(Modifier, it.Content)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .padding(top = 2.dp)
        ) {
            MessageInput(ChatId = ChatId)
        }
    }
}