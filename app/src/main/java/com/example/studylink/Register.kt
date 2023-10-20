package com.example.studylink

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.android.gms.tasks.Task


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun RegisterScreen(navController: NavHostController){
    val Context = LocalContext.current
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var FullName by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val brush1 = Brush.horizontalGradient(listOf(Color(0xFF2C8DFF), Color(0xFF006DEC)))
    val brush2 = Brush.horizontalGradient(listOf(Color(0xFFF5ED37), Color(0xFFCCC51B)))
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(painterResource(id = R.drawable.background), contentScale = ContentScale.FillBounds)){
        Column(modifier = Modifier
            .fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(text = "Study", modifier = Modifier.textBrush(brush1), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "Link", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.textBrush(brush2))
                }
                Card(modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(0.8f), shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Sign Up", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp, textAlign = TextAlign.Center)
                    }
                    Text(text = "Full Name", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 25.dp, top = 10.dp))
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = FullName,
                            onValueChange = {
                                FullName = it
                            },
                            label = { Text(text = "Full Name") },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(65.dp),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                        )
                    }
                    Text(text = "Email", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 25.dp, top = 10.dp))
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            label = { Text(text = "Email") },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(65.dp),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                        )
                    }
                    Text(text = "Password", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 25.dp, top = 10.dp))
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            singleLine = true,
                            placeholder = { Text("Password") },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                // Please provide localized description for accessibility services
                                val description =
                                    if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, contentDescription = "Visibility")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(65.dp),
                            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                                Toast.makeText(Context, "Image Successfully Choosen", Toast.LENGTH_SHORT)
                            }
                        ) {
                            Text("Select your Photo")
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = {
                                println(email.text)
                                auth.createUserWithEmailAndPassword(email.text, password.text).addOnCompleteListener { task ->
                                    if(task.isSuccessful){
                                        val tempMut = mutableListOf<String>()
                                        photoUri?.let {
                                            val resAnjay = StorageUtil.uploadToStorage(
                                                uri = it,
                                                context = Context,
                                                type = "image",
                                                email = email.text,
                                                fullName = FullName.text,
                                                cont = Context
                                            )
                                        }

                                    }else{
                                        Toast.makeText(Context, "Registration Failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(20),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(top = 10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066E4))
                        ) {
                            Text(text = "Sign Up", color = Color.White)
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.End) {
                        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier
                            .fillMaxWidth(0.90f)
                            .padding(end = 25.dp, top = 10.dp))
                    }
                    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Or Sign up with", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 10.dp))
                    }
                    Column (modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally){
                        Row(){
                            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google Icon", modifier = Modifier
                                .height(100.dp)
                                .padding(end = 60.dp)
                                .clickable(onClick = {}))
                            Image(painter = painterResource(id = R.drawable.facecom), contentDescription = "Face Icon", modifier = Modifier
                                .height(100.dp)
                                .clickable(onClick = {}))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Have an account ?", fontSize = 15.sp, fontWeight = FontWeight.SemiBold,)
                        ClickableText(text = AnnotatedString("Login"), onClick = {navController?.navigate(Login.route)}, style = TextStyle(color = Color.Blue), modifier = Modifier.padding(start = 5.dp))
                    }
                }
            }
        }
    }
}