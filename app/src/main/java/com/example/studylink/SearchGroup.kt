package com.example.studylink

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun groupcard(
    modifier: Modifier = Modifier,
    people : List<String>,
    scope : List<String>,
    personcount : Int,
    onCardClick : (List<String>) -> Unit,
    onButtonClick : () -> Unit
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
                    .align(alignment = Alignment.CenterEnd)
                    .requiredSize(size = 40.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = Color(0xff1c1a22))
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
                people.forEach { person ->
                    PersonBox(name = person, onPersonClick = { })
                }
                people.forEach { person ->
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var inputText = rememberSaveable { mutableStateOf("") }
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

@Preview(widthDp = 393)
@Composable
private fun groupCardPv() {
    groupcard(
        people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"),
        scope = listOf("Math", "Science"),
        personcount = 7,
        onCardClick = {  },
        onButtonClick = { /* Perform the button action here */ }
    )
}

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

@Preview(widthDp = 393)
@Composable
fun testViewGroup() {
    var showOverlay = remember { mutableStateOf(false) }
    var selectedPeople = remember { mutableStateOf<List<String>>(listOf()) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(
                color = Color(0xfff1f1f1)
            )
            .padding(bottom = 5.dp)
    ) {
        GroupSplash()
        SearchBar(Modifier.padding(bottom = 3.dp))
        groupcard(
            people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith", "Jamal Hussein", "John Doe", "Jane Smith", "Joanne Canonball"),
            scope = listOf("Math", "Science"),
            personcount = 7,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Jamal Hussein", "John Doe", "Jane Smith"),
            scope = listOf("Science", "Astrology", "Economy"),
            personcount = 5,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Joanne Canonball", "Jane Smith"),
            scope = listOf("Science", "Astrology"),
            personcount = 2,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Alan Becker", "Jane Smith", "Jane Smith"),
            scope = listOf("Science", "Geology", "Law"),
            personcount = 8,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Jamal Hussein", "John Doe", "Jane Smith"),
            scope = listOf("Computer Science", "Physics"),
            personcount = 6,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"),
            scope = listOf("Math", "Science"),
            personcount = 7,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Jamal Hussein", "John Doe", "Jane Smith"),
            scope = listOf("Science", "Astrology", "Economy"),
            personcount = 5,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Joanne Canonball", "Jane Smith"),
            scope = listOf("Science", "Astrology"),
            personcount = 2,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Alan Becker", "Jane Smith", "Jane Smith"),
            scope = listOf("Science", "Geology", "Law"),
            personcount = 8,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
        groupcard(
            people = listOf("Jamal Hussein", "John Doe", "Jane Smith"),
            scope = listOf("Computer Science", "Physics"),
            personcount = 6,
            onCardClick = { people ->
                showOverlay.value = true
                selectedPeople.value = people
            },
            onButtonClick = { /* Perform the button action here */ }
        )
    }

    if (showOverlay.value) {
        overlayGroupInfo(
            people = selectedPeople.value,
            onDismissRequest = { showOverlay.value = it }
        )
    }
}