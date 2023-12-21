package com.example.studylink

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.os.Handler
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

val LocalIsDark = compositionLocalOf { mutableStateOf(false) }

@Composable
fun ProfileTopNavbar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 60.dp)
            .background(color = defaultColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Your Profile",
                    color = headText,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
                Text(text = inRefresh.value.toString(), color = Color.Transparent)
            }
        }
    }
}

@Composable
fun userStatus(launchers: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .padding(bottom = 10.dp)) {

        Row(modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 10.dp)) {
            val painter = rememberAsyncImagePainter(model = currUser.value.imageURL)
            AnimatedBorderProfile(
                image = painter,
                launchers = launchers,
                shape = CircleShape,
                modifier = Modifier
                    .size(80.dp),
                gradient = Brush.linearGradient(listOf(Color.Yellow, Color.Blue))
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Text(
                    text = currUser.value.fullName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = headText
                )
                Text(
                    text = "Online",
                    fontSize = 17.sp,
                    color = subheadText
                )
            }
        }
    }
}

@Composable
fun AnimatedBorderProfile(
    modifier: Modifier = Modifier,
    image: Painter,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 3.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    animationDuration: Int = 10000,
    onCardClick: () -> Unit = {},
    launchers: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier
            .clip(shape)
            .clickable { onCardClick() },
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        ) {
            Button(
                onClick = {
                    launchers.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            ) {
                Image(
                    painter = image,
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun userNameBox(
    contentSpace: Dp
) {
    Button(
        onClick = {
            showOverlayNameProfile.value = true
        },
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.width(contentSpace))
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = currUser.value.fullName,
                        color = headText,
                        style = TextStyle(
                            fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        Text(
                            text = "Name",
                            color = subheadText,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                        Text(
                            text = inRefresh.value.toString(),
                            color = Color.Transparent,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredSize(size = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = subheadText,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Spacer(modifier = Modifier.width(contentSpace))
            }
        }
    }
}

@Composable
fun userBioBox(
    contentSpace: Dp
) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var zerozeropointfive = (screenWidthInDp * 0.05f)

    var textBoxSize = screenWidthInDp - ((2*contentSpace) + (2*zerozeropointfive) + 25.dp)

    Button(
        onClick = {
            showOverlayBioProfile.value = true
        },
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.width(contentSpace))
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Bio",
                        color = headText,
                        style = TextStyle(
                            fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        if (currUser.value.bio == "") {
                            Text(
                                text = "Add a few words about yourself",
                                color = subheadText,
                                style = TextStyle(
                                    fontSize = 13.sp)
                            )
                        } else {
                            Text(
                                text = currUser.value.bio,
                                color = subheadText,
                                style = TextStyle(
                                    fontSize = 13.sp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .width(textBoxSize)
                            )
                        }
                        Text(
                            text = inRefresh.value.toString(),
                            color = Color.Transparent,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredSize(size = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = subheadText,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Spacer(modifier = Modifier.width(contentSpace))
            }
        }
    }
}

@Composable
fun userStuFiBox(
    contentSpace: Dp
) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var zerozeropointfive = (screenWidthInDp * 0.05f)

    var textBoxSize = screenWidthInDp - ((2*contentSpace) + (2*zerozeropointfive) + 25.dp)

    Button(
        onClick = {
            showOverlayStuFiProfile.value = true
        },
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.width(contentSpace))
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = currUser.value.studyField,
                        color = headText,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 16.sp),
                        modifier = Modifier
                            .width(textBoxSize)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        Text(
                            text = "Study Field",
                            color = subheadText,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                        Text(
                            text = inRefresh.value.toString(),
                            color = Color.Transparent,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredSize(size = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = subheadText,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Spacer(modifier = Modifier.width(contentSpace))
            }
        }
    }
}

@Composable
fun userExpBox(
    contentSpace: Dp
) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var zerozeropointfive = (screenWidthInDp * 0.05f)

    var textBoxSize = screenWidthInDp - ((2*contentSpace) + (2*zerozeropointfive) + 45.dp)

    Button(
        onClick = {
            showOverlayExpProfile.value = true
        },
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.width(contentSpace))
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Experience",
                            color = headText,
                            style = TextStyle(
                                fontSize = 16.sp)
                        )
                        Text(
                            text = inRefresh.value.toString(),
                            color = Color.Transparent,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Column {
                        if (currUser.value.strongAt.isEmpty()) {
                            Text(
                                text = "Add a few of your experience",
                                color = subheadText,
                                style = TextStyle(
                                    fontSize = 13.sp)
                            )
                        } else {
                            currUser.value.strongAt.forEach { item ->
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = headText,
                                        modifier = Modifier
                                            .size(14.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = item,
                                        color = subheadText,
                                        style = TextStyle(
                                            fontSize = 13.sp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .width(textBoxSize)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredSize(size = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = subheadText,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Spacer(modifier = Modifier.width(contentSpace))
            }
        }
    }
}

@Composable
fun Account(
    modifier: Modifier = Modifier
) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var contentSpace = (screenWidthInDp * 0.075f)
    var contentSize = (screenWidthInDp * 0.85f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = defaultColor)
    ) {
        Column {
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Text(
                        text = "Account",
                        color = headText,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    )
                    Text(text = inRefresh.value.toString(), color = Color.Transparent)
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Text(
                            text = currUser.value.email,
                            color = headText,
                            style = TextStyle(
                                fontSize = 16.sp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Email",
                            color = subheadText,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(13.dp))
                Row{
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .width(contentSize)
                    )
                }

                userNameBox(contentSpace = contentSpace)

                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .width(contentSize)
                    )
                }

                userStuFiBox(contentSpace = contentSpace)

                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .width(contentSize)
                    )
                }

                userExpBox(contentSpace = contentSpace)

                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .width(contentSize)
                    )
                }
                
                userBioBox(contentSpace = contentSpace)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun overlayNameChange() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var dasBio = remember { mutableStateOf(currUser.value.fullName) }
    val color = remember { mutableStateOf(subheadText) }

    ModalBottomSheet(
        onDismissRequest = {
            showOverlayNameProfile.value = false
            dasBio.value = ""
        },
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetState = sheetState,
        containerColor = cardsColor,
        contentColor = Color.Transparent,
        dragHandle = { },
        windowInsets = WindowInsets.ime,
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        val context = LocalContext.current
        val displayMetrics = context.resources.displayMetrics

        val screenWidthInDp = with(LocalDensity.current) {
            displayMetrics.widthPixels.dp / density
        }

        val maxWidthTextField = (screenWidthInDp - 40.dp)

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = cardsColor)
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Enter your name",
                        color = headText,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    value = mutableStateOf(dasBio.value),
                    onValueChange = { dasBio.value = it },
                    modifier = Modifier
                        .width(maxWidthTextField)
                        .background(Color.Transparent)
                        .onFocusChanged { focusState ->
                            when (focusState.isFocused) {
                                true -> color.value = Color(0xFFFFC600)
                                false -> color.value = dividerColor
                            }
                        },
                    useClear = false,
                    fontSize = 17.sp,
                    singleLine = true,
                    placeholderText = "Enter Name",
                    imeAction = ImeAction.Done,
                    useCounter = true,
                    maxChar = 20
                )
                Spacer(modifier = Modifier.height(1.dp))
                Divider(
                    color = color.value,
                    modifier = Modifier
                    .width(maxWidthTextField-24.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    TextButton(
                        onClick = {
                            dasBio.value = ""

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showOverlayNameProfile.value = false
                                }
                            }
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    TextButton(
                        onClick = {
                            println("WAS IST DAS ${dasBio.value} und ${currUser.value.id}")
                            db.collection("Users")
                                .document(currUser.value.id)
                                .update("fullName", dasBio.value)
                                .addOnSuccessListener {
                                    currUser.value.fullName = dasBio.value
                                    dasBio.value = ""

                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showOverlayNameProfile.value = false
                                        }
                                    }

                                    inRefresh.value++
                                }.addOnFailureListener{
                                    Toast.makeText(context,"There is error happen",Toast.LENGTH_SHORT)
                                }
                        }
                    ) {
                        Text(
                            text = "Save",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun overlayBioChange() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var dasBio = remember { mutableStateOf(currUser.value.bio) }
    val color = remember { mutableStateOf(subheadText) }

    ModalBottomSheet(
        onDismissRequest = {
            showOverlayBioProfile.value = false
        },
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetState = sheetState,
        containerColor = cardsColor,
        contentColor = Color.Transparent,
        dragHandle = { },
        windowInsets = WindowInsets.ime,
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        val context = LocalContext.current
        val displayMetrics = context.resources.displayMetrics

        val screenWidthInDp = with(LocalDensity.current) {
            displayMetrics.widthPixels.dp / density
        }

        val maxWidthTextField = (screenWidthInDp - 40.dp)

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = cardsColor)
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Enter your bio",
                        color = headText,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    value = mutableStateOf(dasBio.value),
                    onValueChange = { dasBio.value = it },
                    modifier = Modifier
                        .width(maxWidthTextField)
                        .background(Color.Transparent)
                        .onFocusChanged { focusState ->
                            when (focusState.isFocused) {
                                true -> color.value = Color(0xFFFFC600)
                                false -> color.value = dividerColor
                            }
                        },
                    useClear = false,
                    fontSize = 17.sp,
                    singleLine = false,
                    maxLine = 3,
                    placeholderText = "Few words about yourself",
                    imeAction = ImeAction.Done,
                    useCounter = true,
                    maxChar = 80,
                    counterPadding = 14.dp
                )
                Spacer(modifier = Modifier.height(1.dp))
                Divider(
                    color = color.value,
                    modifier = Modifier
                        .width(maxWidthTextField-24.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    TextButton(
                        onClick = {
                            dasBio.value = ""

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showOverlayBioProfile.value = false
                                }
                            }
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    TextButton(
                        onClick = {
                            db.collection("Users")
                                .document(currUser.value.id)
                                .update("bio", dasBio.value)
                                .addOnSuccessListener {
                                    currUser.value.bio = dasBio.value
                                    dasBio.value = ""

                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showOverlayBioProfile.value = false
                                        }
                                    }

                                    inRefresh.value++
                                }.addOnFailureListener{
                                    Toast.makeText(context,"There is error happen",Toast.LENGTH_SHORT)
                                }
                        }
                    ) {
                        Text(
                            text = "Save",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun overlayStuFiChange() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var dasBio = remember { mutableStateOf(currUser.value.studyField) }

    ModalBottomSheet(
        onDismissRequest = {
            showOverlayStuFiProfile.value = false
        },
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetState = sheetState,
        containerColor = cardsColor,
        contentColor = Color.Transparent,
        dragHandle = { },
        windowInsets = WindowInsets.ime,
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        val context = LocalContext.current
        val displayMetrics = context.resources.displayMetrics

        val screenWidthInDp = with(LocalDensity.current) {
            displayMetrics.widthPixels.dp / density
        }

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = cardsColor)
                        .fillMaxWidth()
                        .height(58.dp)
                ) {
                    Text(
                        text = "Select Your Study Field",
                        color = headText,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    listOfMajors.forEach() { item ->
                        Row(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        dasBio.value = item
                                    }
                                )
                                .fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = (item == dasBio.value),
                                modifier = Modifier.padding(all = Dp(value = 8F)),
                                onClick = {
                                    dasBio.value = item
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF00B0FF),
                                    unselectedColor = Color(0xFF986DF2),
                                )
                            )
                            Text(
                                text = item,
                                color = headText,
                                modifier = Modifier.padding(start = 16.dp, top = 20.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    TextButton(
                        onClick = {
                            dasBio.value = ""

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showOverlayStuFiProfile.value = false
                                }
                            }
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    TextButton(
                        onClick = {
                            db.collection("Users")
                                .document(currUser.value.id)
                                .update("studyField", dasBio.value)
                                .addOnSuccessListener {
                                    currUser.value.studyField = dasBio.value
                                    dasBio.value = ""

                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showOverlayStuFiProfile.value = false
                                        }
                                    }

                                    inRefresh.value++
                                }.addOnFailureListener{
                                    Toast.makeText(context,"There is error happen",Toast.LENGTH_SHORT)
                                }
                        }
                    ) {
                        Text(
                            text = "Save",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun overlayExpChange() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var dasBio = remember { mutableStateOf("") }
    val color = remember { mutableStateOf(subheadText) }

    ModalBottomSheet(
        onDismissRequest = {
            showOverlayExpProfile.value = false
        },
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetState = sheetState,
        containerColor = cardsColor,
        contentColor = Color.Transparent,
        dragHandle = { },
        windowInsets = WindowInsets.ime,
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        val context = LocalContext.current
        val displayMetrics = context.resources.displayMetrics

        val screenWidthInDp = with(LocalDensity.current) {
            displayMetrics.widthPixels.dp / density
        }

        val maxWidthTextField = (screenWidthInDp - 40.dp)

        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = cardsColor)
                        .fillMaxWidth()
                        .height(58.dp)
                ) {
                    Text(
                        text = "Experience",
                        color = headText,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                ) {
                    currUser.value.strongAt.forEach() { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                            ) {
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = headText,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .align(Alignment.CenterVertically)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = item,
                                    color = subheadText,
                                    style = TextStyle(
                                        fontSize = 16.sp),
                                    maxLines = 1,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                            ) {
                                IconButton(
                                    onClick = {
                                        // Delete The Experience from List
                                            val tempStrongtemp = currUser.value.strongAt.filter { it != item }
                                            val tempStrong = tempStrongtemp.toMutableList()
                                        db.collection("Users")
                                            .document(currUser.value.id)
                                            .update("strongAt", tempStrong)
                                            .addOnSuccessListener {
                                                currUser.value.strongAt = tempStrong
                                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                                    if (!sheetState.isVisible) {
                                                        showOverlayExpProfile.value = false
                                                    }
                                                }

                                            }.addOnFailureListener{
                                                Toast.makeText(context,"There is error happen",Toast.LENGTH_SHORT)
                                            }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    value = mutableStateOf(dasBio.value),
                    onValueChange = { dasBio.value = it },
                    modifier = Modifier
                        .width(maxWidthTextField)
                        .background(Color.Transparent)
                        .onFocusChanged { focusState ->
                            when (focusState.isFocused) {
                                true -> color.value = Color(0xFFFFC600)
                                false -> color.value = dividerColor
                            }
                        },
                    useClear = false,
                    fontSize = 17.sp,
                    singleLine = false,
                    maxLine = 3,
                    placeholderText = "Ex: Kotlin / Canva / Figma / Unity",
                    imeAction = ImeAction.Done,
                    useCounter = true,
                    maxChar = 20,
                    counterPadding = 14.dp
                )
                Spacer(modifier = Modifier.height(1.dp))
                Divider(
                    color = color.value,
                    modifier = Modifier
                        .width(maxWidthTextField-24.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    TextButton(
                        onClick = {
                            dasBio.value = ""

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showOverlayExpProfile.value = false
                                }
                            }
                        }
                    ) {
                        Text(
                            text = "Close",
                            color = subheadText,
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    TextButton(
                        onClick = {
                            val tempSize = currUser.value.strongAt.size
                            if(tempSize < 3){
                                val tempStrong = currUser.value.strongAt
                                tempStrong.add(dasBio.value)
                                currUser.value.strongAt = tempStrong
                                db.collection("Users")
                                    .document(currUser.value.id)
                                    .update("strongAt", tempStrong)
                                    .addOnSuccessListener {
                                        currUser.value.strongAt = tempStrong
                                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                                            if (!sheetState.isVisible) {
                                                showOverlayExpProfile.value = false
                                            }
                                        }

                                    }.addOnFailureListener{
                                        Toast.makeText(context,"There is error happen",Toast.LENGTH_SHORT)
                                    }

                            }else{
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showOverlayExpProfile.value = false
                                    }
                                }
                                Toast.makeText(context,"Experience max is 3",Toast.LENGTH_LONG).show()
                            }
                            dasBio.value = ""
                            inRefresh.value++

                        }
                    ) {
                        Text(
                            text = "Add",
                            color = Color(0xffffc600),
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun ThemeSwitch(
    contentSpace: Dp
) {
    val switchColor by animateColorAsState(if (isDark.value) Color.Black else Color.Gray,
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.Transparent)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    isDark.value = !isDark.value
                    coloringSchema()
                    inRefresh.value++
                }
            )
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
        ) {
            Spacer(modifier = Modifier.width(contentSpace))
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(
                    text = "Theme",
                    color = headText,
                    style = TextStyle(
                        fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Text(
                        text = schemaCond.value,
                        color = subheadText,
                        style = TextStyle(
                            fontSize = 13.sp)
                    )
                    Text(
                        text = inRefresh.value.toString(),
                        color = Color.Transparent,
                        style = TextStyle(
                            fontSize = 13.sp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
        ) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Switch(
                    checked = isDark.value,
                    onCheckedChange = {
                        isDark.value = it
                        coloringSchema()
                        inRefresh.value++
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = switchColor,
                        uncheckedThumbColor = switchColor
                    )
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            Spacer(modifier = Modifier.width(contentSpace))
        }
    }
}

@Composable
fun signOutBtn(
    contentSpace: Dp,
    navController: NavHostController
) {
    Button(
        onClick = {
            auth.signOut()
            navController.navigate(Login.route) {
                popUpTo(Login.route) {
                    inclusive = true
                }
            }
        },
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.width(contentSpace))
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Row {
                        Text(
                            text = "Logout",
                            color = Color.Red,
                            style = TextStyle(
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = inRefresh.value.toString(),
                            color = Color.Transparent,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .requiredSize(size = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "logout icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Spacer(modifier = Modifier.width(contentSpace))
            }
        }
    }
}

@Composable
fun SettingFrag(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var context = LocalContext.current
    var displayMetrics = context.resources.displayMetrics

    var screenWidthInDp = with(LocalDensity.current) {
        displayMetrics.widthPixels.dp / density
    }

    var contentSpace = (screenWidthInDp * 0.075f)
    var contentSize = (screenWidthInDp * 0.85f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = defaultColor)
    ) {
        Column {
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Text(
                        text = "Settings",
                        color = headText,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    )
                    Text(text = inRefresh.value.toString(), color = Color.Transparent)
                }
                Spacer(modifier = Modifier.height(10.dp))

                ThemeSwitch(contentSpace = contentSpace)

                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .width(contentSize)
                    )
                }

                signOutBtn(
                    contentSpace = contentSpace,
                    navController = navController
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
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
                UpdateProfileUtil.uploadToStorage(uri = mediaUri!!, context = context, cont = context)
            }
        }

        mediaUri = null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = inRefresh.value.toString(), color = Color.Transparent)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background)
        ) {
            ProfileTopNavbar()
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                userStatus(launchers = launcher)
                Account()
                Spacer(modifier = Modifier.height(15.dp))
                SettingFrag(navController = navController)
            }
        }

        if (showOverlayNameProfile.value) {
            overlayNameChange()
        }
        if (showOverlayBioProfile.value) {
            overlayBioChange()
        }
        if (showOverlayStuFiProfile.value) {
            overlayStuFiChange()
        }
        if (showOverlayExpProfile.value) {
            overlayExpChange()
        }
    }
}

@Preview(widthDp = 393)
@Composable
fun pppv() {
    ProfileScreen(navController = rememberNavController())
}
