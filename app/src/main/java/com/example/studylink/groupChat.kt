package com.example.studylink

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupLeftChat(modifier: Modifier = Modifier, sender: String, message : String) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var seventyPercentOfScreenWidth = (screenWidthInDp * 0.7f)

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
                    painter = painterResource(id = R.drawable.ic_profile_foreground),
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
                            text = sender,
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

@Preview(widthDp = 393)
@Composable
fun GroupLeftChatPv() {
    GroupLeftChat(sender = "Jonathan A", message = "hahahoibafobfabiofbbaobfibaosifoiaboibfbaiobfobaofbaofbaefaibfoiabofibaoifboaibfbaebfioabfoabfibaiofbiob")
}