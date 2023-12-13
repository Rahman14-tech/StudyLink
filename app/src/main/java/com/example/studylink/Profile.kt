package com.example.studylink

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Collections
import java.util.Collections.rotate

@Composable
fun ProfileTopNavbar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 60.dp)
            .background(color = Color.White)
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
                    color = Color(0xff202020),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview(widthDp = 393)
@Composable
private fun TopNavbarPreview() {
    ProfileTopNavbar()
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
                        color = Color(0xff202020),
                        style = TextStyle(
                            fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Name",
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontSize = 13.sp)
                    )
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
                        tint = Color(0xff444444),
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
                        color = Color(0xff202020),
                        style = TextStyle(
                            fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Add a few words about yourself",
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontSize = 13.sp)
                    )
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
                        tint = Color(0xff444444),
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
                        color = Color(0xff202020),
                        style = TextStyle(
                            fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Add a few words about yourself",
                        color = Color.DarkGray,
                        style = TextStyle(
                            fontSize = 13.sp)
                    )
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
                        tint = Color(0xff444444),
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
            .background(color = Color.White)
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
                        color = Color(0xff202020),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    )
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
                            color = Color(0xff202020),
                            style = TextStyle(
                                fontSize = 16.sp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Email",
                            color = Color.DarkGray,
                            style = TextStyle(
                                fontSize = 13.sp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(13.dp))
                Row{
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = Color(0xff959595),
                        modifier = Modifier
                            .width(contentSize)
                    )
                }

                userNameBox(contentSpace = contentSpace)

                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = Color(0xff959595),
                        modifier = Modifier
                            .width(contentSize)
                    )
                }

                userExpBox(contentSpace = contentSpace)

                Row {
                    Spacer(modifier = Modifier.width(contentSpace))
                    Divider(
                        color = Color(0xff959595),
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

@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    image: Painter,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    borderWidth: Dp = 2.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Gray, Color.White)),
    animationDuration: Int = 10000,
    onCardClick: () -> Unit = {},
    launchers: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    val brush1 = Brush.horizontalGradient(listOf(Color(0xFF2C8DFF), Color(0xFF006DEC)))
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
            Image(
                painter = image,
                contentDescription = "User Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = {
                            launchers.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    )
            )
        }
    }
}

@Composable
fun userStatus(launchers: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color(0xFFF1F1F1))
        .padding(bottom = 10.dp)) {

        Row(modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 10.dp)) {
            val painter = rememberAsyncImagePainter(model = currUser.value.imageURL)
            AnimatedBorderCard(
                image = painter,
                launchers = launchers,
                shape = CircleShape,
                modifier = Modifier
                    .size(80.dp),
                gradient = Brush.linearGradient(listOf(Color(0xffffc600), Color.Cyan))
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Text(
                    text = currUser.value.fullName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Online", fontSize = 17.sp)
            }
        }
    }
}

@Preview(widthDp = 393)
@Composable
private fun AccountPreview() {
    Account()
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun overlayNameChange() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var dasBio = remember { mutableStateOf(currUser.value.fullName) }
    val color = remember { mutableStateOf(Color.Gray) }

    ModalBottomSheet(
        onDismissRequest = {
            showOverlayNameProfile.value = false
            dasBio.value = ""
        },
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetState = sheetState,
        containerColor = Color.White,
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
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .height(58.dp)
                ) {
                    Text(
                        text = "Enter your name",
                        color = Color.Black,
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
                        .background(Color.White)
                        .onFocusChanged { focusState ->
                            when (focusState.isFocused) {
                                true -> color.value = Color(0xFFFFC600)
                                false -> color.value = Color.Gray
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
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(widthDp = 393)
@Composable
private fun overlayNamePreview() {
    overlayNameChange()
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun overlayBioChange() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val color = remember { mutableStateOf(Color.Gray) }

    ModalBottomSheet(
        onDismissRequest = {
            showOverlayBioProfile.value = false
            inputText.value = ""
        },
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetState = sheetState,
        containerColor = Color.White,
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
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .height(58.dp)
                ) {
                    Text(
                        text = "Enter your bio",
                        color = Color.Black,
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
                    value = mutableStateOf(inputText.value),
                    onValueChange = { inputText.value = it },
                    modifier = Modifier
                        .width(maxWidthTextField)
                        .background(Color.White)
                        .onFocusChanged { focusState ->
                            when (focusState.isFocused) {
                                true -> color.value = Color(0xFFFFC600)
                                false -> color.value = Color.Gray
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
                            inputText.value = ""

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
                            val docRef = Firebase.firestore.collection("Users").document(currUser.value.id)

                            docRef.update("fullName", inputText.value)
                            currUser.value.fullName = inputText.value

                            inputText.value = ""

                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showOverlayBioProfile.value = false
                                }
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
        Spacer(modifier = Modifier.height(15.dp))
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff1f1f1))
                .verticalScroll(rememberScrollState())
        ) {
            ProfileTopNavbar()
            Column {
                userStatus(launchers = launcher)
                Account()
            }
        }

        if (showOverlayNameProfile.value) {
            overlayNameChange()
        }
        if (showOverlayBioProfile.value) {
            overlayBioChange()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreens(navController: NavHostController) {
    val isEditingBio = remember { mutableStateOf(false) }


    Scaffold(
            topBar = {
                ProfileTopNavbar()
            }
    ) { contentPadding ->
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding) // Add padding to the entire profile content
        ) {
            if (currUser != null) {
                val editedBio = remember { mutableStateOf(TextFieldValue(currUser.value.fullName)) }
                Spacer(modifier = Modifier.height(15.dp))

                Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                    Text(text = "Account", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Column {
                        Row() {
                            Text(text = currUser.value.fullName, fontSize = 20.sp)
                        }
                        Text(text = "\nName", fontSize = 20.sp)
                        Divider(color = Color.Black, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(15.dp))

                        Row {
                            Text(text = currUser.value.email, fontSize = 20.sp)
                        }
                        Text(text = "\nEmail", fontSize = 20.sp)
                        Divider(color = Color.Black, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(15.dp))

                        Row {
                            if (isEditingBio.value) {
                                Column(
                                        modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                                ) {
                                    OutlinedTextField(
                                            value = editedBio.value,
                                            onValueChange = {
                                                editedBio.value = it
                                            },
                                            label = { Text("Edit Bio") },
                                            modifier = Modifier
                                                    .fillMaxWidth()
                                    )
                                }
                            } else {
                                Text(text = "I'm strong at ${currUser.value.strongAt}, and I want to study ${currUser.value.wantStudy}", fontSize = 20.sp)
                            }

                            // Please fix this shit, it's not appearing on the app
                            IconButton(
                                    onClick = {
                                        isEditingBio.value = false
                                    },
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .size(20.dp)
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Save,
                                        contentDescription = "Save",
                                        modifier = Modifier.size(20.dp)
                                )
                            }

                        }

                        Text(text = "\nBio", fontSize = 20.sp)
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box (modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color.LightGray)
                )
                Row(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                    Text(text = "Settings", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Text(text = "User not found")
            }
        }
    }
}

@Preview(widthDp = 393)
@Composable
fun pppv() {
    ProfileScreen(navController = rememberNavController())
}
