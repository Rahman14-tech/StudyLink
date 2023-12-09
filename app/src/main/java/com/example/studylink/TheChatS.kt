package com.example.studylink

import VideoPlayerScreen
import android.R.attr.path
import android.R.attr.textSize
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Date
import android.webkit.MimeTypeMap
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.Dp
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestion
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

var inputText = mutableStateOf("")
@RequiresApi(Build.VERSION_CODES.O)
fun SendMessage(TheMessage: String, ChatId: String){
    val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
    val currentDate = sdf.format(Date())
    db.collection("Chats").document(ChatId).collection("ChatData").add(hashMapOf(
        "Content" to TheMessage,
        "TheUser" to currUser.value.email,
        "TimeSent" to currentDate,
        "MediaType" to "",
        "ContentMedia" to "",
    ))
}



@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    useClear: Boolean = true,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = true,
    maxLine: Int = 1,
    fontSize: TextUnit = 15.sp,
    value: MutableState<String>,
    onValueChange: (String) -> Unit,
    maxChar: Int = 8,
    useCounter: Boolean = false,
    counterPadding: Dp = 5.dp
) {
    val focusRequester = remember { FocusRequester() }
    val maxLength = maxChar

    BasicTextField(
        modifier = modifier
            .background(Color(0x00ffffff))
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    focusRequester.requestFocus()
                }
            },
        value = value.value,
        onValueChange = {
            if (useCounter) {
                if (it.length <= maxLength) {
                    value.value = it
                    onValueChange(it)
                }
            }
            else {
                value.value = it
                onValueChange(it)
            }
        },
        singleLine = singleLine,
        maxLines = maxLine,
        textStyle = LocalTextStyle.current.copy(
            color = Color.Black,
            fontSize = fontSize
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(5.dp))
                }
                Box(
                    Modifier.weight(1f)
                ) {
                    if (value.value.isEmpty()) {
                        Text(
                            placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = Color(0xff767676),
                                fontSize = fontSize
                            )
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
                if (useClear && !value.value.isEmpty()) {
                    IconButton(
                        onClick = {
                            value.value = ""
                            onValueChange("")
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Cancel,
                            null,
                            tint = Color.DarkGray.copy(alpha = 0.8f)
                        )
                    }
                }
                if (useCounter) {
                    Text(
                        text = "${maxLength - value.value.length}",
                        fontSize = fontSize,
                        color = Color(0xff4b4b4b),
                        modifier = Modifier
                            .padding(start = counterPadding)
                    )
                }
            }
        }
    )
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
                    onClick = {
                        navController.navigate(Dashboard.route) {
                            popUpTo(YourChats.route) {
                                inclusive = true
                            }
                        }
                    },
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
                        )
                    )
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

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(modifier: Modifier = Modifier, ChatId:String , launchers: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,isGroup:Boolean) {

    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics

    val screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    val maxWidthTextField = (screenWidthInDp - 90.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(maxWidthTextField + 30.dp)
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row {
                TextButton(
                    onClick = {
                        launchers.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = modifier
                        .requiredSize(size = 45.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .requiredSize(size = 30.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color.DarkGray)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.addicon),
                            contentDescription = null,
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 5.dp, y = 5.dp)
                                .requiredSize(size = 20.dp))
                    }
                }
                CustomTextField(
                    value = mutableStateOf(inputText.value),
                    onValueChange = {
                        inputText.value = it
                    },
                    leadingIcon = null,
                    trailingIcon = null,
                    modifier = Modifier
                        .background(
                            Color(0x00ededed),
                            RoundedCornerShape(percent = 100)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .heightIn(min = 45.dp)
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 8.dp, top = 5.dp, bottom = 5.dp),
                    placeholderText = "Type a Message",
                    useClear = false,
                    imeAction = ImeAction.None,
                    singleLine = false,
                    maxLine = 5,
                    fontSize = 16.sp
                )
            }
        }
        TextButton(
            onClick = {
                if(inputText.value != ""){
                    if(!isGroup){
                        SendMessage(TheMessage = inputText.value, ChatId = ChatId)
                    }else{
                        SendMessageGroup(TheMessage = inputText.value, GroupChatId = ChatId)
                    }
                    inputText.value = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .requiredSize(size = 45.dp)
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 45.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = Color(0xffffc600))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sendicon),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .requiredSize(size = 22.dp))
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
fun LeftChat(modifier: Modifier = Modifier, message : String,timeSent: String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var eightyPercentOfScreenWidth = (screenWidthInDp * 0.8f)
    var splittedtime = timeSent.split(" ").toTypedArray()
    var thehour = splittedtime[1]
    var hournmin = thehour.subSequence(0,5)

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 5.dp, bottom = 5.dp),
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
                text = hournmin.toString(),
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
fun MediaLeftChat(ChatId:String, navController: NavHostController,modifier: Modifier = Modifier, MediaContent : String,MediaType:String,timeSent: String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }
    var eightyPercentOfScreenWidth = (screenWidthInDp * 0.8f)
    var splittedtime = timeSent.split(" ").toTypedArray()
    var thehour = splittedtime[1]
    var hournmin = thehour.subSequence(0,5)
        Box(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    content = {
                        if (MediaType == "Video") {
                            VideoPlayerScreen(ChatId = ChatId, navController = navController,MediaContent)
                        } else if (MediaType == "Image") {
                            Image(
                                painter = rememberAsyncImagePainter(MediaContent),
                                contentDescription = "The Image", modifier = Modifier.size(250.dp)
                            )
                        }
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
                    text = hournmin.toString(),
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
fun RightChat(modifier: Modifier = Modifier, message : String,timeSent: String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }
    var eightyPercentOfScreenWidth = (screenWidthInDp * 0.8f)
    var splittedtime = timeSent.split(" ").toTypedArray()
    var thehour = splittedtime[1]
    var hournmin = thehour.subSequence(0,5)

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = hournmin.toString(),
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


@SuppressLint("SuspiciousIndentation")
@Composable
fun MediaRightChat(ChatId:String, navController: NavHostController,modifier: Modifier = Modifier, MediaContent : String,MediaType:String,timeSent: String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }
    var eightyPercentOfScreenWidth = (screenWidthInDp * 0.8f)
    var splittedtime = timeSent.split(" ").toTypedArray()
    var thehour = splittedtime[1]
    var hournmin = thehour.subSequence(0,5)
        Box(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = hournmin.toString(),
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
                        if(MediaType == "Video"){
                            VideoPlayerScreen(ChatId = ChatId, navController = navController,mediaUrl = MediaContent)
                        }else if(MediaType == "Image"){
                            Image(painter = rememberAsyncImagePainter(MediaContent), contentDescription = "The Image", modifier = Modifier
                                .size(250.dp)
                                .fillMaxHeight())
                        }
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatSystem(navController: NavHostController, ChatId: String) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics

    val screenHeightInDp = with(LocalDensity.current) {
        displayMetrics.heightPixels.dp / density
    }
    val conversationML: MutableList<TextMessage> = mutableListOf()
    val maxHeightChatDisplay = (screenHeightInDp - 68.dp)
    var mediaUri: Uri? by rememberSaveable { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        mediaUri = uri
    }
    if(mediaUri != null){
        val contentResolver: ContentResolver = context.contentResolver
        val mimeType = getMimeType(contentResolver, mediaUri!!)
        val testType = rememberSaveable {
            mutableStateOf("")
        }
        testType.value = mimeType!!
        print("HESITATION $mimeType")
        if (mimeType != null) {
                if (mimeType.startsWith("image")) {
                    MediaChatUtil.uploadToStorage(context = context, uri = mediaUri!!, type = "Image", ChatId = ChatId, isGroup = false)

                } else if (mimeType.startsWith("video")) {
                    MediaChatUtil.uploadToStorage(context = context, uri = mediaUri!!, type = "Video", ChatId = ChatId, isGroup = false)
                }
        }

        mediaUri = null
    }

    LaunchedEffect(Unit){
        ChatData.clear()
        GetTheChats(ChatId)
    }
    if(ChatData.isNotEmpty()){
        if(conversationML.isNotEmpty()){
            conversationML.clear()
        }
        ChatData.forEach{

            if(it.TheUser == currUser.value.email){
                if(it.Content == ""){
                    conversationML.add(TextMessage.createForLocalUser("Here Some Image/Videos",System.currentTimeMillis()))
                }else{
                    conversationML.add(TextMessage.createForLocalUser(it.Content,System.currentTimeMillis()))
                }

            }else{
                if(it.Content == ""){
                    conversationML.add(TextMessage.createForRemoteUser("Here some Image/Videos",System.currentTimeMillis(),it.TheUser))
                }else{
                    conversationML.add(TextMessage.createForRemoteUser(it.Content,System.currentTimeMillis(),it.TheUser))
                }

            }
        }
        val smartReplyGenerator = SmartReply.getClient()
        var tempRes: MutableList<SmartReplySuggestion> = mutableListOf<SmartReplySuggestion>()
        smartReplyGenerator.suggestReplies(conversationML)
            .addOnSuccessListener { result ->
                if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                    // The conversation's language isn't supported, so
                    // the result doesn't contain any suggestions.\
                    println("Apaansih")
                    theSmartReply = mutableStateListOf()
                } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                    // Task completed successfully
                    // ...
                    tempRes = result.suggestions
                    println("NGEN ${tempRes}")


                }
            }
            .addOnFailureListener {
                println("Apaansih2")
                theSmartReply = mutableStateListOf()
            }.addOnCompleteListener{
                theSmartReply.clear()
                tempRes.forEach{
                    theSmartReply.add(it.text)
                    println("GEBURTSTAG ${it.text}")
                }
            }


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
                            if(it.Content != ""){
                                LeftChat(Modifier,it.Content, timeSent = it.TimeSent)
                            }else{
                                MediaLeftChat(ChatId = ChatId,navController = navController,MediaContent = it.ContentMedia, MediaType = it.MediaType, timeSent = it.TimeSent)

                            }

                        }else{
                            if(it.Content != ""){
                                RightChat(Modifier, it.Content, timeSent = it.TimeSent)
                            }else{
                                    MediaRightChat(
                                        ChatId = ChatId,
                                        navController = navController,
                                        MediaContent = it.ContentMedia,
                                        MediaType = it.MediaType,
                                        timeSent = it.TimeSent
                                    )

                                }

                        }
                    }
                }
            }
            Column {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 5.dp)
                ) {
                    theSmartReply.forEach{
                        println(" $it")
                        Card(modifier = Modifier
                            .padding(2.dp), onClick = {inputText.value = it}) {
                            Column(modifier = Modifier.padding(5.dp)) {
                                Text(text = it, fontSize = 20.sp)
                            }

                        }
                    }

                }
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {

                    MessageInput(ChatId = ChatId, launchers = launcher, isGroup = false)
                }
            }

            }
}
fun getMimeType(contentResolver: ContentResolver, uri: Uri): String? {
    return contentResolver.getType(uri)
}
@Composable
fun MediaPreview(mediaUri:Uri?){


}