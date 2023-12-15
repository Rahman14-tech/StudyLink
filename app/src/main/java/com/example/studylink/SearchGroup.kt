package com.example.studylink

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@Composable
fun groupcard(
    modifier: Modifier = Modifier,
    people : List<String>,
    groupId: String,
    scope : List<String>,
    personcount : Int,
    onCardClick : (List<String>) -> Unit,
    onButtonClick : () -> Unit,
    groupName: String
) {
    val scopeSeparator = " | "
    val scopes = buildAnnotatedString {
        scope.forEachIndexed { index, string ->
            append(string)
            if (index < scope.size - 1) {
                append(scopeSeparator)
            }
        }
    }
    val peopleName = mutableListOf<String>()
    for (datum in people){
        val tempName = Realusers.firstOrNull{it.email == datum}
        if(tempName != null){
            peopleName.add(tempName.fullName)
        }
    }
    val allpeople = peopleName.joinToString(", ")

    val peoples = if (allpeople.length > 22) {
        allpeople.substring(0, 22) + "..."
    } else {
        allpeople
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardsColor,
            contentColor = cardsColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 15.dp, top = 3.dp, bottom = 3.dp)
            .clickable(onClick = { onCardClick(people) })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .requiredSize(size = 80.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = Color(0xffffc600))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.groupicon),
                        contentDescription = "image (group)",
                        modifier = Modifier
                            .align(alignment = Alignment.TopStart)
                            .offset(x = 8.dp, y = 8.dp)
                            .requiredSize(size = 64.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 5.dp)
                        .requiredHeight(70.dp)
                        .align(alignment = Alignment.CenterVertically)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Row {
                            Text(
                                text = groupName,
                                color = headText,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = " #${groupId.substring(0,10)}",
                                color = headText,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Text(
                            text = scopes,
                            color = subheadText,
                            style = TextStyle(
                                fontSize = 13.sp),
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                        )
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 48.dp)
                                .requiredHeight(height = 24.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(color = subheadText)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 5.dp, end = 5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.personicon2),
                                    contentDescription = "person image",
                                    modifier = Modifier
                                        .requiredSize(size = 14.dp)
                                        .align(alignment = Alignment.CenterVertically)
                                )
                                Text(
                                    text = "${people.size}/$personcount",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 12.sp),
                                    modifier = Modifier
                                        .align(alignment = Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .requiredSize(size = 40.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = groupButtonColor)
                    .clickable(
                        onClick = onButtonClick
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chaticon2),
                    contentDescription = "chat icon",
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .requiredSize(size = 30.dp)
                )
            }
        }
    }
}

@Composable
fun PersonBox(
    name: String,
    onPersonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
            .padding(top = 5.dp, bottom = 5.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onPersonClick
            )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = Color(0xffffc600))
                    .size(38.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.personicon2),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = name,
                color = Color.Black,
                style = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun overlayGroupInfo(
    people: List<String>,
    onDismissRequest: (Boolean) -> Unit
) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var screenHeightInDp = with(LocalDensity.current) {
        displayMetrics.heightPixels.dp / density
    }

    var overlayHeight = (screenHeightInDp * 0.6f)
    var overlayWidth = (screenWidthInDp * 0.85f)
    val peopleName = mutableListOf<String>()
    for (datum in people){
        val tempName = Realusers.firstOrNull{it.email == datum}
        if(tempName != null){
            peopleName.add(tempName.fullName)
        }
    }

    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .width(overlayWidth)
                .height(overlayHeight)
                .heightIn(max = 420.dp)
                .widthIn(max = 300.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .height(58.dp)
            ) {
                Text(
                    text = "Group Info",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .padding(start = 15.dp)
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(
                        color = Color(0xfff1f1f1)
                    )
            ) {
                peopleName.forEach { person ->
                    PersonBox(name = person, onPersonClick = { })
                }
            }
        }
    }
}

@Preview(widthDp = 393)
@Composable
fun OverlayGroupInfoPv() {
    overlayGroupInfo(
        people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith", "Jamal Hussein", "John Doe", "Jane Smith", "Joanne Canonball"),
        onDismissRequest = { }
    )
}

@Composable
fun GroupSplash(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = defaultColor)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .wrapContentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.groupsplash),
                contentDescription = "group vector art",
                modifier = Modifier
                    .requiredWidth(width = 225.dp)
                    .requiredHeight(height = 176.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = headText,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold)
                    ) {append("Find")}
                    withStyle(style = SpanStyle(
                        color = headText,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Normal)
                    ) {append(" Your Study\nGroup Here")}},
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .offset(x = (-13).dp, y = 0.dp)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var inputText = rememberSaveable { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 42.dp)
            .background(color = defaultColor)
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .wrapContentSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            CustomTextField(
                value = mutableStateOf(inputText.value),
                onValueChange = {
                    inputText.value = it
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
                placeholderText = "Search Group",
                useClear = true,
                imeAction = ImeAction.Search
            )
        }
    }
}

//@Preview(widthDp = 393)
//@Composable
//private fun groupCardPv() {
//    groupcard(
//        people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"),
//        scope = listOf("Math", "Science"),
//        personcount = 7,
//        onCardClick = {  },
//        onButtonClick = { /* Perform the button action here */ }
//    )
//}

@Preview(widthDp = 393)
@Composable
private fun SplashPreview() {
    GroupSplash()
}

@Preview(widthDp = 393)
@Composable
private fun SearchBarPreview() {
    SearchBar()
}


@Composable
fun butDialog(onDismissRequest: (Boolean) -> Unit, cont: Context) {
    var inputDial = remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit){
        selectedOptionText.value = "Choose here"
    }
    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(14.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(modifier = Modifier.fillMaxSize()){
                Text(text = "Create a group", modifier = Modifier
                    .fillMaxWidth()
                    .padding(), textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Group hashtag (up to 3)", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 5.dp), textAlign = TextAlign.Start, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "Ex: Computer,Mathematics,Biology", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 5.dp), textAlign = TextAlign.Start, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                CustomTextField(
                    value = inputDial,
                    onValueChange = {
                        inputDial.value = it
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
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(end = 8.dp, top = 20.dp, bottom = 5.dp),
                    placeholderText = "Type a Hashtag",
                    useClear = false,
                    imeAction = ImeAction.None,
                    singleLine = false,
                    maxLine = 5,
                    fontSize = 16.sp,
                )
                Text(text = "Choose Your Major", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 5.dp), textAlign = TextAlign.Start, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                DropDownSubjects()


                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
                    Button(
                        onClick = {
                            var seperatedTag = inputDial.value.split(",")
                            if(seperatedTag.size > 3){
                                seperatedTag = listOf(seperatedTag[0],seperatedTag[1],seperatedTag[2])
                            }
                            val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
                            val currentDate = sdf.format(Date())

                            if(selectedOptionText.value != "Choose here" && inputDial.value != ""){
                                db.collection("Chatgroup").add(hashMapOf(
                                    "groupFocus" to selectedOptionText.value,
                                    "groupName" to classCode[selectedOptionText.value],
                                    "hashTag" to seperatedTag,
                                    "maxMember" to 8,
                                    "members" to mutableListOf<String>(currUser.value.email),
                                    "timeCreated" to currentDate
                                )).addOnSuccessListener {
                                    Toast.makeText(cont, "Creation Success", Toast.LENGTH_LONG).show()
                                    inputDial.value ="Choose here"
                                }.addOnFailureListener{
                                    Toast.makeText(cont, "Creation Failed", Toast.LENGTH_LONG).show()
                                    inputDial.value = "Choose here"
                                }
                            }
                                  },
                        shape = RoundedCornerShape(20),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(bottom = 10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066E4)),
                    ) {
                        Text(text = "Create Group", color = Color.White)
                    }
                }

            }

        }
    }
}
@Composable
fun testViewGroup(navController: NavHostController) {
    var showOverlay = remember { mutableStateOf(false) }
    var selectedPeople = remember { mutableStateOf<List<String>>(listOf()) }
    var context = LocalContext.current

    if(Filteredusers.isEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff1f1f1)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Loading...", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
            Text(text = "Stuck at loading? Then there is no group", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp))
        }
    } else {
        if(showAutoMatch.value) {
            butDialog(onDismissRequest = { showAutoMatch.value = it },
                cont = context
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = background
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 5.dp)
            ) {
                GroupSplash()
                SearchBar(Modifier.padding(bottom = 3.dp))
                groupChatsDashboard.map {
                    groupcard(
                        people = it.members,
                        groupName = it.groupName,
                        scope = it.hashTag,
                        personcount = it.maxMember,
                        onCardClick = { people ->
                            showOverlay.value = true
                            selectedPeople.value = people
                        },
                        groupId = it.id,
                        onButtonClick = {
                            if(it.members.size >= it.maxMember){
                                Toast.makeText(context, "The group is full.", Toast.LENGTH_LONG).show()
                            }else{
                                if(it.members.contains(currUser.value.email)){
                                    Toast.makeText(context,"You've enrolled to this group", Toast.LENGTH_LONG).show()
                                }else{
                                    val damember = it.members
                                    damember.add(currUser.value.email)
                                    db.collection("Chatgroup").document(it.id).update("members",damember).addOnSuccessListener {sucIt ->
                                        Toast.makeText(context,"Successfully join group", Toast.LENGTH_LONG)
                                        navController.navigate(GroupChats.route+"/{${it.id}}")
                                    }
                                }

                            }
                        }
                    )
                }
            }
            Button(
                onClick = {
                    showAutoMatch.value = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .wrapContentWidth()
                    .height(40.dp)
                    .offset(x = (-5).dp, y = (-8).dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Auto Match")
            }
            if (showOverlay.value) {
                overlayGroupInfo(
                    people = selectedPeople.value,
                    onDismissRequest = { showOverlay.value = it }
                )
            }
        }
    }
}