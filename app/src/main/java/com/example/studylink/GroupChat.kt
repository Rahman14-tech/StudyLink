package com.example.studylink

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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

@Composable
fun groupcard(modifier: Modifier = Modifier, people : List<String>, scope : List<String>, personcount : Int) {
    val scopeSeparator = " | "
    val scopes = buildAnnotatedString {
        scope.forEachIndexed { index, string ->
            append(string)
            if (index < scope.size - 1) {
                append(scopeSeparator)
            }
        }
    }

    val allpeople = people.joinToString(", ")

    val peoples = if (allpeople.length > 22) {
        allpeople.substring(0, 22) + "..."
    } else {
        allpeople
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
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
                        Text(
                            text = peoples,
                            color = Color(0xff202020),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                        )
                        Text(
                            text = scopes,
                            color = Color.DarkGray,
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
                                .background(color = Color(0xff848484))
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
                                    text = "$personcount/8",
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
                    .wrapContentSize()
                    .align(alignment = Alignment.CenterEnd)
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .requiredSize(size = 40.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = Color(0xff1c1a22))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chaticon2),
                        contentDescription = "chat icon",
                        modifier = Modifier
                            .align(alignment = Alignment.TopStart)
                            .offset(x = 5.dp, y = 5.dp)
                            .requiredSize(size = 30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Splash(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
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
                        color = Color(0xff202020),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold)
                    ) {append("Find")}
                    withStyle(style = SpanStyle(
                        color = Color(0xff202020),
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

@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder"
) {
    var inputText = rememberSaveable { mutableStateOf("") }
    BasicTextField(
        modifier = modifier
            .background(Color(0xffededed)),
        value = inputText.value,
        onValueChange = {
            inputText.value = it
        },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            color = Color.Black,
            fontSize = 15.sp
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    Modifier.weight(1f)
                ) {
                    if (inputText.value.isEmpty()) {
                        Text(
                            placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = Color(0xff767676),
                                fontSize = 15.sp
                            )
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
                if (!inputText.value.isEmpty()) {
                    IconButton(
                        onClick = {
                            inputText.value = ""
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
fun SearchBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 42.dp)
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .wrapContentSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            CustomTextField(
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
                placeholderText = "Search Group"
            )
        }
    }
}

@Preview(widthDp = 393)
@Composable
private fun groupCardPv() {
    groupcard(people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"), scope = listOf("Math", "Science"), personcount = 7)
}

@Preview(widthDp = 393)
@Composable
private fun SplashPreview() {
    Splash()
}

@Preview(widthDp = 393)
@Composable
private fun SearchBarPreview() {
    SearchBar()
}

@Preview(widthDp = 393)
@Composable
fun testViewGroup() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Splash()
        SearchBar()
        groupcard(people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"), scope = listOf("Math", "Science"), personcount = 7)
        groupcard(people = listOf("Jamal Hussein", "John Doe", "Jane Smith"), scope = listOf("Science", "Astrology", "Economy"), personcount = 5)
        groupcard(people = listOf("Joanne Canonball", "Jane Smith"), scope = listOf("Science", "Astrology"), personcount = 2)
        groupcard(people = listOf("Alan Becker", "Jane Smith", "Jane Smith"), scope = listOf("Science", "Geology", "Law"), personcount = 8)
        groupcard(people = listOf("Jamal Hussein", "John Doe", "Jane Smith"), scope = listOf("Computer Science", "Physics"), personcount = 6)
        groupcard(people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"), scope = listOf("Math", "Science"), personcount = 7)
        groupcard(people = listOf("Jamal Hussein", "John Doe", "Jane Smith"), scope = listOf("Science", "Astrology", "Economy"), personcount = 5)
        groupcard(people = listOf("Joanne Canonball", "Jane Smith"), scope = listOf("Science", "Astrology"), personcount = 2)
        groupcard(people = listOf("Alan Becker", "Jane Smith", "Jane Smith"), scope = listOf("Science", "Geology", "Law"), personcount = 8)
        groupcard(people = listOf("Jamal Hussein", "John Doe", "Jane Smith"), scope = listOf("Computer Science", "Physics"), personcount = 6)
    }
}