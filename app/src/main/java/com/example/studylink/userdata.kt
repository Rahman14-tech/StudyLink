package com.example.studylink

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue


val currUser = mutableStateOf(TextFieldValue(""))
val tempUrl = mutableStateOf(TextFieldValue(""))
data class ProfileFirestore(
    val email: String = "",
    val fullName: String = "",
    val imageURL: String = "",
    val strongAt: MutableList<String> = mutableListOf(),
    val wantStudy: MutableList<String> = mutableListOf()
)

data class YourChatsType(
    val FkUsers: MutableList<String> = mutableListOf(),
    var id: String = "",
)
val Realusers = mutableStateListOf<ProfileFirestore>(

)

var Filteredusers = mutableStateListOf<ProfileFirestore>()
