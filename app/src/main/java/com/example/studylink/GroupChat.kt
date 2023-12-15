package com.example.studylink

import VideoPlayerScreen
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestion
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TopNavbarGroup(modifier: Modifier = Modifier, navController: NavHostController, ChatId: String) {
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
                    Image(painter = painterResource(id = R.drawable.groupicon), contentScale = ContentScale.Crop, contentDescription = "Gambar Wong", modifier = Modifier
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
                        text = "Anjay Group",
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
@Composable
fun GroupLeftChat(modifier: Modifier = Modifier,timeSent:String, sender: String, message : String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var seventyPercentOfScreenWidth = (screenWidthInDp * 0.7f)
    var splittedtime = timeSent.split(" ").toTypedArray()
    var thehour = splittedtime[1]
    var hournmin = thehour.subSequence(0,5)
    val theOtherUser = Realusers.firstOrNull{ it.email == sender }
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
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.Top)
                    .requiredSize(size = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .requiredSize(size = 32.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color(0xffffc600))
                )
                Image(
                    painter = rememberAsyncImagePainter(model = theOtherUser!!.imageURL),
                    contentDescription = "user image",
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .clip(shape = CircleShape)
                        .requiredSize(size = 32.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Spacer(modifier = Modifier.height(6.dp))
                Box(
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
                ) {
                    Column {
                        Text(
                            text = theOtherUser!!.fullName,
                            color = Color(0xff202020),
                            style = TextStyle(
                                fontSize = 16.sp, fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                                .widthIn(max = seventyPercentOfScreenWidth)
                                .padding(top = 5.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
                        )
                        Text(
                            text = message,
                            color = Color(0xff202020),
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                                .widthIn(max = seventyPercentOfScreenWidth)
                                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                        )
                    }
                }
            }
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
fun GetTheGroupChats(GroupChatId: String){
    db.collection("Chatgroup").document(GroupChatId).collection("ChatData").addSnapshotListener{snapshot, e ->
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

@Composable
fun GroupMediaLeftChat(ChatId:String, navController: NavHostController,modifier: Modifier = Modifier, MediaContent : String,MediaType:String,timeSent: String) {
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
@RequiresApi(Build.VERSION_CODES.O)
fun SendMessageGroup(TheMessage: String, GroupChatId: String){
    val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
    val currentDate = sdf.format(Date())
    db.collection("Chatgroup").document(GroupChatId).collection("ChatData").add(hashMapOf(
        "Content" to TheMessage,
        "TheUser" to currUser.value.email,
        "TimeSent" to currentDate,
        "MediaType" to "",
        "ContentMedia" to "",
    ))
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupChatSystem(navController: NavHostController, GroupChatId: String){
    LaunchedEffect(Unit){
        ChatData.clear()
        GetTheGroupChats(GroupChatId)
    }
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
                MediaChatUtil.uploadToStorage(context = context, uri = mediaUri!!, type = "Image", ChatId = GroupChatId, isGroup = true)

            } else if (mimeType.startsWith("video")) {
                MediaChatUtil.uploadToStorage(context = context, uri = mediaUri!!, type = "Video", ChatId = GroupChatId, isGroup = true)
            }
        }

        mediaUri = null
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
            TopNavbarGroup(navController = navController, ChatId = GroupChatId)
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
                    println("NEGUS2 ${it.Content}")
                    if(it.TheUser != currUser.value.email){
                        if(it.Content != ""){
                            GroupLeftChat(Modifier, timeSent = it.TimeSent, sender = it.TheUser, message = it.Content)
                        }else{
                            GroupMediaLeftChat(ChatId = GroupChatId,navController = navController,MediaContent = it.ContentMedia, MediaType = it.MediaType, timeSent = it.TimeSent)
                        }

                    }else{
                        if(it.Content != ""){
                            RightChat(Modifier, it.Content, timeSent = it.TimeSent)
                        }else{
                            MediaRightChat(
                                ChatId = GroupChatId,
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

                MessageInput(ChatId = GroupChatId, launchers = launcher, isGroup = true)
            }
        }

    }
}

@Preview(widthDp = 393)
@Composable
fun GroupLeftChatPv() {
    GroupLeftChat(sender = "Jonathan A",timeSent = "16:04", message = "hahahoibafobfabiofbbaobfibaosifoiaboibfbaiobfobaofbaofbaefaibfoiabofibaoifboaibfbaebfioabfoabfibaiofbiob")
}