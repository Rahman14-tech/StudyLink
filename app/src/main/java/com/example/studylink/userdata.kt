package com.example.studylink

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue

val currScreen = mutableStateOf(TextFieldValue(""))
val tempUrl = mutableStateOf(TextFieldValue(""))
data class ProfileFirestore(
    val email: String = "",
    val fullName: String = "",
    val imageURL: String = "",
    var bio: String = "",
    val strongAt: MutableList<String> = mutableListOf(),
    val wantStudy: MutableList<String> = mutableListOf(),
)
val Realusers = mutableStateListOf<ProfileFirestore>(

)
