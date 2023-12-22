package com.example.studylink

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.ktx.toObject
var searchYourChat:MutableState<String> =  mutableStateOf("")
fun GetChatData(){
    db.collection("Chats").addSnapshotListener{snapshot, e ->
        if (e != null) {
            Log.w(ContentValues.TAG, "Listen failed.", e)
            return@addSnapshotListener
        }
        if(snapshot != null && !snapshot.isEmpty){
            val list = snapshot.documents
            for(datum in list){
                val c: YourChatsType? = datum.toObject(YourChatsType::class.java)
                c?.id = datum.id
                if(c!= null){
                    tempTheChat.add(c)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourChatsCardPersonal(datum: YourChatsType, navController: NavHostController) {
    println("OHARANG2 ${datum.id}")
    val tempPartnerEmail = datum.FkUsers.contains(currUser.value.email)

    if(tempPartnerEmail) {
        val tempNottheone = datum.FkUsers.firstOrNull { it != currUser.value.email }
        val tempPartnerProfile = Realusers.firstOrNull { it.email == tempNottheone }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = background),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            onClick = {
                navController?.navigate(TheChatS.route+"/${datum.id}")
            }
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(60.dp)
                        .background(Color(0xFFFFC600))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(tempPartnerProfile!!.imageURL),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Gambar Wong",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${tempPartnerProfile!!.fullName}",
                            fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            color = headText
                        )
                        Text(
                            text = datum.theLastTime,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = subheadText
                        )
                    }
                    Text(
                        text = datum.theLast,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = subheadText,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourChatsCardPersonalSearched(datum: YourChatsType, navController: NavHostController){
    println("OHARANG2 ${datum.id}")
    val tempPartnerEmail = datum.FkUsers.contains(currUser.value.email)

    if(tempPartnerEmail) {
        val tempNottheone = datum.FkUsers.firstOrNull { it != currUser.value.email }
        val tempPartnerProfile = Realusers.firstOrNull { it.email == tempNottheone }
        if(tempPartnerProfile!!.fullName.lowercase().contains(searchYourChat.value.lowercase())) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(containerColor = background),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                onClick = {
                    navController?.navigate(TheChatS.route+"/${datum.id}")
                }
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(60.dp)
                            .background(Color(0xFFFFC600))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(tempPartnerProfile!!.imageURL),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Gambar Wong",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${tempPartnerProfile!!.fullName}",
                                fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                color = headText
                            )
                            Text(
                                text = datum.theLastTime,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                color = subheadText
                            )
                        }
                        Text(
                            text = datum.theLast,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = subheadText,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourChatsCardGroup(datum: GroupChatType, navController: NavHostController){
    println("OHARANG2 ${datum.id}")
    val tempPartnerEmail = datum.members.contains(currUser.value.email)
    var showOverlay = remember { mutableStateOf(false) }
    var selectedPeople = remember { mutableStateOf<List<String>>(listOf()) }
    val context = LocalContext.current
    if(tempPartnerEmail){
        groupcard(
            people = datum.members,
            scope = datum.hashTag,
            personcount = datum.maxMember,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = {
                navController?.navigate(GroupChats.route+"/${datum.id}")
            },
            groupId = datum.id,
            groupName = datum.groupName
        )
    }
}
@Composable
fun YourChatsCardGroupSearched(datum: GroupChatType, navController: NavHostController){
    println("OHARANG2 ${datum.id}")
    val tempPartnerEmail = datum.members.contains(currUser.value.email)
    var showOverlay = remember { mutableStateOf(false) }
    var selectedPeople = remember { mutableStateOf<List<String>>(listOf()) }
    val context = LocalContext.current
    println("JSM 23 ${searchYourChat.value}")
    if(tempPartnerEmail){
        if(datum.id.lowercase().contains(searchYourChat.value.lowercase()) || datum.groupName.lowercase().contains(searchYourChat.value.lowercase())) {
            groupcard(
                people = datum.members,
                scope = datum.hashTag,
                personcount = datum.maxMember,
                onCardClick = { people ->
                    showOverlay.value = true
                    selectedPeople.value = people
                },
                onButtonClick = {
                    navController?.navigate(GroupChats.route+"/${datum.id}")
                },
                groupId = datum.id,
                groupName = datum.groupName
            )
        }

    }
}

@Composable
fun CustomTextField2(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    useClear: Boolean = true,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = true,
    maxLine: Int = 1,
    fontSize: TextUnit = 15.sp,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .background(Color(0x00ffffff)),
        value = searchYourChat.value,
        onValueChange = {
            searchYourChat.value = it
            onValueChange(it)
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
                    if (searchYourChat.value.isEmpty()) {
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
                if (useClear && !searchYourChat.value.isEmpty()) {
                    IconButton(
                        onClick = {
                            searchYourChat.value = ""
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
            }
        }
    )
}
@Composable
fun YourChatScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        tempTheChat.removeAll(tempTheChat)
        GetChatData()
        groupChatsDashboard.removeAll(groupChatsDashboard)
        GetGroupChatData()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = defaultColor)
            .fillMaxHeight()
    ) {
        Header("YourChatScreen")
        if(showYourChatSearch.value) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = defaultColor)
            ) {
                CustomTextField2(
                    onValueChange = {
                        searchYourChat.value = it
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            null,
                            tint = LocalContentColor.current.copy(alpha = 0.3f)
                        )
                    },
                    trailingIcon = null,
                    modifier = Modifier
                        .background(
                            Color(0xffededed),
                            RoundedCornerShape(percent = 100)
                        )
                        .clip(shape = RoundedCornerShape(16.dp))
                        .height(30.dp)
                        .fillMaxWidth(),
                    placeholderText = "Search Chat",
                    useClear = true,
                    imeAction = ImeAction.Search
                )
            }
        }
        val tempChat = tempTheChat.filter { it.FkUsers.contains(currUser.value.email) }
        println("TEMPSS $tempChat")
        if(tempTheChat.isEmpty() || tempChat.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = defaultColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nochats),
                    contentDescription = "Loading or Empty Chats"
                )
                Text(
                    text = "You haven't start any chat yet or refresh it",
                    fontSize = 20.sp,
                    color = headText,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            if(selectedPeerChats.value) {
                if(searchYourChat.value != "") {
                    LazyColumn(
                        modifier = Modifier
                            .background(defaultColor)
                    ) {
                        itemsIndexed(tempTheChat) { _, datum ->
                            YourChatsCardPersonalSearched(datum, navController)
                        }
                    }
                } else if(searchYourChat.value == "") {
                    LazyColumn(
                        modifier = Modifier
                            .background(defaultColor)
                    ) {
                        itemsIndexed(tempTheChat) { _, datum ->
                            YourChatsCardPersonal(datum, navController)
                        }
                    }
                }
            } else {

                if(searchYourChat.value != ""){
                    LazyColumn(
                        modifier = Modifier
                            .background(defaultColor)
                    ) {
                        itemsIndexed(groupChatsDashboard) { _, datum ->
                            YourChatsCardGroupSearched(datum, navController)
                        }
                    }

                }else{
                    LazyColumn(
                        modifier = Modifier
                            .background(defaultColor)
                    ) {
                        itemsIndexed(groupChatsDashboard) { _, datum ->
                            YourChatsCardGroup(datum, navController)
                        }
                    }
                }

            }

        }
    }
}

@Preview(widthDp = 393)
@Composable
fun chatscreenpv() {
    YourChatScreen(navController = rememberNavController())
}