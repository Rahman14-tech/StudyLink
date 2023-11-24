package com.example.studylink

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.google.mlkit.nl.smartreply.SmartReplySuggestion
import java.util.Date


val currUser = mutableStateOf(ProfileFirestore())
val tempUrl = mutableStateOf(TextFieldValue(""))
data class ProfileFirestore(
    val email: String = "",
    val fullName: String = "",
    val imageURL: String = "",
    val strongAt: MutableList<String> = mutableListOf(),
    val wantStudy: MutableList<String> = mutableListOf(),
)

data class YourChatsType(
    val FkUsers: MutableList<String> = mutableListOf(),
    var id: String = "",
    var isGroup: Boolean = false
)

data class ChatDataType(
    val Content: String = "",
    val TheUser: String = "",
    val TimeSent: String = "",
    val MediaType: String = "",
    val ContentMedia: String = "",
)
val Realusers = mutableStateListOf<ProfileFirestore>(

)

var theSmartReply= mutableStateListOf<String>()

var Filteredusers = mutableStateListOf<ProfileFirestore>()

val tempTheChat = mutableStateListOf<YourChatsType>()

val ChatData = mutableStateListOf<ChatDataType>()
fun removeCurrUser(){
    Filteredusers.remove(currUser.value)
}

var selectedPeerDashboard = mutableStateOf<Boolean>(true)

