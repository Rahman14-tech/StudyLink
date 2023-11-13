package com.example.studylink

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.google.android.gms.tasks.Tasks
import okhttp3.internal.wait
import kotlin.math.log
@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun DashboardScreen (navController: NavHostController) {
    val states =  Filteredusers.filter { it.email != currUser.value.text }.reversed().map { it to rememberSwipeableCardState() }
    var nameindex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    if(Filteredusers.size == 0){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Loading...", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
        }
    }else{
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White), ) {
            Header("dashboard")
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)) {
                Box(){
                    states.forEach { (profile, state) ->
                        if (state.swipedDirection == null) {
                            UserCard(modifier = Modifier
                                .swipableCard(
                                    state = state,
                                    blockedDirections = listOf(Direction.Down),
                                    onSwiped = { nameindex++ },
                                    onSwipeCancel = {
                                        println("The swiping was cancelled")
                                    }
                                ), prof = profile, nameindex)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserCard (modifier: Modifier, prof: ProfileFirestore, nameindex: Int){
    Box(modifier = modifier){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Card (modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.9f), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)){
                Column() {
                    Image(painter = rememberAsyncImagePainter(prof.imageURL), contentDescription = prof.fullName, modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(), alignment = Alignment.TopStart, ContentScale.FillBounds)

                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(), verticalArrangement = Arrangement.Bottom) {
                    Text(text = Realusers[nameindex].fullName, color = Color.Black, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 25.dp, bottom = 20.dp, top = 5.dp))
                    Row(modifier = Modifier.padding( bottom = 15.dp)) {
                        Text(text = "+", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 25.dp))
                        Realusers[nameindex].strongAt.map {
                            Text(text = "$it", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 5.dp))
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row() {
                            Text(
                                text = "-",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 25.dp)
                            )
                            Realusers[nameindex].wantStudy.map {
                                Text(
                                    text = "$it",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }
                        Row() {
                            Card(shape = CircleShape, modifier = Modifier
                                .padding(end = 20.dp)
                                .height(50.dp)
                                .width(50.dp)
                                .background(color = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                                Icon(imageVector = Icons.Outlined.Star, contentDescription = "", tint = Color(0xFFFFB347), modifier = Modifier.fillMaxSize())
                            }
                        }
                    }
                }
            }
        }
    }
}


