package com.example.studylink

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.google.mlkit.nl.smartreply.SmartReplySuggestion
import java.util.Date


val currUser = mutableStateOf(ProfileFirestore())
val tempUrl = mutableStateOf(TextFieldValue(""))
data class ProfileFirestore(
    val id: String = "",
    val email: String = "",
    val fullName: String = "",
    val imageURL: String = "",
    val strongAt: MutableList<String> = mutableListOf(),
    val wantStudy: MutableList<String> = mutableListOf(),
    val studyField: String = "",
)

data class YourChatsType(
    val FkUsers: MutableList<String> = mutableListOf(),
    var id: String = "",
    var isGroup: Boolean = false
)
data class GroupChatType(
    var id: String = "",
    var groupFocus: String = "",
    val hashTag:MutableList<String> = mutableListOf<String>(),
    val members:MutableList<String> = mutableListOf(),
    var maxMember: Int = 10
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
val groupChatsDashboard = mutableStateListOf<GroupChatType>()

val tempTheChat = mutableStateListOf<YourChatsType>()

val ChatData = mutableStateListOf<ChatDataType>()
fun removeCurrUser(){
    Filteredusers.remove(currUser.value)
}

var selectedPeerDashboard = mutableStateOf<Boolean>(true)
var selectedPeerChats = mutableStateOf<Boolean>(true)
var showYourChatSearch = mutableStateOf(false)
var showDashfilterPersonal = mutableStateOf(false)
var showOverlayNameProfile = mutableStateOf(false)
var showOverlayBioProfile = mutableStateOf(false)
var showBottomSheet = mutableStateOf(false)
val listOfMajors = listOf<String>("Agriculture, and Related Sciences","Architecture and Related Services","Area, Ethnic, Cultural, Gender, and Group Studies","Aviation","Biological and Biomedical Sciences","Business, Management, and Related Support Services","Communication, Journalism, and Related Programs","Communications Technologies/technicians","Computer and Information Sciences","Construction Trades","Engineering","English Language and Literature/letters","Family and Consumer Sciences/human Sciences","Foreign Languages","Health Professions","History","Human Services","Mathematics and Statistics","Mechanic and Repair Technologies/technicians","Military Technologies and Applied Sciences","Natural Resources and Conservation","Culinary Services","Philosophy and Religious Studies","Physical Sciences","Psychology","Social Sciences","Theology and Religious Vocations","Transportation and Materials Moving","Visual and Performing Arts","Others")
