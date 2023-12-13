package com.example.studylink

import android.content.ContentValues
import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.withTimeout
import okhttp3.internal.wait
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Handler
import kotlin.math.log
var selectedOption = mutableStateOf("All Posts")
@Composable
fun dashDialog(onDismissRequest: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(14.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            LazyColumn(){
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (selectedOption == selectedOption),
                                onClick = {
                                    selectedOption.value = "All Posts"
                                    onDismissRequest(false)
                                }
                            )
                            .padding(horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = ("All Posts" == selectedOption.value),
                            modifier = Modifier.padding(all = Dp(value = 8F)),
                            onClick = {
                                selectedOption.value = "All Posts"
                                onDismissRequest(false)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF00B0FF),
                                unselectedColor = Color(0xFF986DF2),
                            )
                        )
                        Text(
                            text = "All Posts",
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                        )
                    }
                }
                itemsIndexed(listOfMajors) { _, datum ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (selectedOption == selectedOption),
                                onClick = {
                                    selectedOption.value = datum
                                    onDismissRequest(false)
                                }
                            )
                            .padding(horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = (datum == selectedOption.value),
                            modifier = Modifier.padding(all = Dp(value = 8F)),
                            onClick = {
                                selectedOption.value = datum
                                onDismissRequest(false)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF00B0FF),
                                unselectedColor = Color(0xFF986DF2),
                            )
                        )
                        Text(
                            text = datum,
                            modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                        )
                    }
                }
            }
        }
    }
}
fun CreatePersonalChat(peerEmail:String, navController: NavHostController){
    var alreadyContacted = false
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
                    if(!c.isGroup){
                        if(c.FkUsers.containsAll(listOf(peerEmail, currUser.value.email))){
                            alreadyContacted = true
                            break
                        }
                    }
                }
            }
            if(alreadyContacted){
                navController.navigate(YourChats.route)
            }else{
                db.collection("Chats").add(hashMapOf(
                    "FkUsers" to mutableListOf<String>(currUser.value.email, peerEmail),
                )).addOnSuccessListener {
                    navController.navigate(TheChatS.route+"/${it.id}")
                }.addOnFailureListener{
                    tempUrl.value = TextFieldValue("")
                }
            }
        }
    }

}
@Composable
fun DashboardScreen (navController: NavHostController) {
    if(selectedPeerDashboard.value){
        peerDashboard(navController = navController)
    }else{
        groupDashboard(navController = navController)
    }
}
fun GetGroupChatData(){
    db.collection("Chatgroup").addSnapshotListener{snapshot, e ->
        if (e != null) {
            Log.w(ContentValues.TAG, "Listen failed.", e)
            return@addSnapshotListener
        }
        if(snapshot != null && !snapshot.isEmpty){
            val list = snapshot.documents
            for(datum in list){
                val c: GroupChatType? = datum.toObject(GroupChatType::class.java)
                c?.id = datum.id
                if(c!= null){
                    groupChatsDashboard.add(c)
                }
            }
        }
    }
}

@Composable
fun groupDashboard(navController: NavHostController){
    LaunchedEffect(Unit){
        groupChatsDashboard.removeAll(groupChatsDashboard)
        GetGroupChatData()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xfff1f1f1)), ) {
        Header("dashboard")
        testViewGroup(navController)
    }
}
@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun peerDashboard(navController: NavHostController){
    removeCurrUser()
    LaunchedEffect(Unit){
        Filteredusers.removeAll(Filteredusers)
        GetUsersData()
    }
    var finfilt by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if(Filteredusers.isEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff1f1f1)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Loading...", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
        }
    }else{
        for (filtereduser in Filteredusers) {
            println("Siapa aja tuh $filtereduser")
        }
        if(showDashfilterPersonal.value){
            dashDialog(onDismissRequest = {showDashfilterPersonal.value = it})
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff1f1f1)), ) {
            Header("dashboard")
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)) {
                Box(){
                    if(selectedOption.value != "All Posts"){
                        val filteredUserDashboard = Filteredusers.filter { it.studyField ==  selectedOption.value}
                        val states =  filteredUserDashboard.reversed().map { it to rememberSwipeableCardState() }
                        states.forEach { (profile, state) ->
                            if (state.swipedDirection == null) {
                                UserCard(modifier = Modifier
                                    .swipableCard(
                                        state = state,
                                        blockedDirections = listOf(Direction.Down),
                                        onSwiped = {  },
                                        onSwipeCancel = {
                                            println("The swiping was cancelled")
                                        }
                                    ), prof = profile,navController = navController)
                            }
                        }
                    }else{
                        val states =  Filteredusers.reversed().map { it to rememberSwipeableCardState() }
                        states.forEach { (profile, state) ->
                            if (state.swipedDirection == null) {
                                UserCard(modifier = Modifier
                                    .swipableCard(
                                        state = state,
                                        blockedDirections = listOf(Direction.Down),
                                        onSwiped = {  },
                                        onSwipeCancel = {
                                            println("The swiping was cancelled")
                                        }
                                    ), prof = profile,navController = navController)
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun UserCard (modifier: Modifier = Modifier, prof: ProfileFirestore, navController: NavHostController){
    Box(modifier = modifier){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.9f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Column {
                        Image(
                            painter = rememberAsyncImagePainter(prof.imageURL),
                            contentDescription = prof.fullName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.7f),
                            alignment = Alignment.Center,
                            ContentScale.Crop
                        )
                        Text(
                            text = prof.fullName,
                            color = Color.Black,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 25.dp, bottom = 10.dp, top = 5.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.padding(bottom = 10.dp)
                                ) {
                                    Text(
                                        text = "+",
                                        color = Color(0xff373737),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(start = 25.dp)
                                    )
                                    prof.strongAt.map {
                                        Text(
                                            text = "$it",
                                            color = Color(0xff373737),
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(start = 5.dp)
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 15.dp), horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = "-",
                                            color = Color(0xff373737),
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(start = 25.dp)
                                        )
                                        prof.wantStudy.map {
                                            Text(
                                                text = "$it",
                                                color = Color(0xff373737),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(start = 5.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .requiredSize(size = 55.dp)
                            .offset(x = (-30).dp, y = (-40).dp)
                            .align(alignment = Alignment.BottomEnd)
                            .clip(shape = CircleShape),
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xfff7f7f7))
                    ) {
                        TextButton(
                            onClick = { CreatePersonalChat(prof.email, navController) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.staricon),
                                contentDescription = null,
                                modifier = Modifier
                                    .requiredSize(size = 35.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview(widthDp = 393)
//@Composable
//private fun groupCardPv() {
//    UserCard()
//}
