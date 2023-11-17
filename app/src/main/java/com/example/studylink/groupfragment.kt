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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun groupcards(modifier: Modifier = Modifier, people : List<String>, scope : List<String>, personcount : Int) {
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

@Preview(widthDp = 393)
@Composable
private fun groupCardPv() {
    groupcards(people = listOf("Alan Becker", "Joanne Canonball", "John Doe", "Jane Smith"), scope = listOf("Math", "Science"), personcount = 7)
}