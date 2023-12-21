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
    var id: String = "",
    val email: String = "",
    var fullName: String = "",
    var imageURL: String = "",
    var strongAt: MutableList<String> = mutableListOf(),
    var studyField: String = "",
    var bio: String = ""
)

data class YourChatsType(
    val FkUsers: MutableList<String> = mutableListOf(),
    var id: String = "",
    var theLast : String = "",
    var theLastTime: String = "",
)
data class GroupChatType(
    var id: String = "",
    var groupFocus: String = "",
    val hashTag:MutableList<String> = mutableListOf<String>(),
    val members:MutableList<String> = mutableListOf(),
    var maxMember: Int = 10,
    val groupName: String = ""
)


data class ChatDataType(
    var id: String = "",
    val Content: String = "",
    val TheUser: String = "",
    val TimeSent: String = "",
    val DateSent: String = "",
    val MediaType: String = "",
    val ContentMedia: String = "",
    val OrderNo: Int = 0
)

val Realusers = mutableStateListOf<ProfileFirestore>(

)

var theSmartReply= mutableStateListOf<String>()

var Filteredusers = mutableStateListOf<ProfileFirestore>()
val groupChatsDashboard = mutableStateListOf<GroupChatType>()

val tempTheChat = mutableStateListOf<YourChatsType>()

val ChatData = mutableStateListOf<ChatDataType>()
val TempChatData = mutableStateListOf<ChatDataType>()
var sortChatTrue = mutableStateOf(false)
fun removeCurrUser(){
    Filteredusers.remove(currUser.value)
}
val classCode = mapOf<String,String>(
    "Agriculture, and Related Sciences" to "AGR",
    "Architecture and Related Services" to "ARC",
    "Area, Ethnic, Cultural, Gender, and Group Studies" to "AECG",
    "Aviation" to "AVI",
    "Biological and Biomedical Sciences" to "BIO",
    "Business, Management, and Related Support Services" to "BMR",
    "Communication, Journalism, and Related Programs" to "CJR",
    "Communications Technologies/technicians" to "CT",
    "Computer and Information Sciences" to "CSC",
    "Construction Trades" to "CTRA",
    "Engineering" to "ENGI",
    "English Language and Literature/letters" to "ELL",
    "Family and Consumer Sciences/human Sciences" to "FCS",
    "Foreign Languages" to "FLS",
    "Health Professions" to "HP",
    "History" to "HIST",
    "Human Services" to "HUS",
    "Mathematics and Statistics" to "MATH",
    "Mechanic and Repair Technologies/technicians" to "MECH",
    "Military Technologies and Applied Sciences" to "MIL",
    "Natural Resources and Conservation" to "NRC",
    "Culinary Services" to "CULS",
    "Philosophy and Religious Studies" to "PHIL",
    "Physical Sciences" to "PHYSCI",
    "Psychology" to "PSY",
    "Social Sciences" to "SOSC",
    "Theology and Religious Vocations" to "TRV",
    "Transportation and Materials Moving" to "TMM",
    "Visual and Performing Arts" to "ARTS",
    "Others" to "OTH",
)

var alphaSettingSelected = mutableStateOf(0.6f)

var selectedPeerDashboard = mutableStateOf<Boolean>(true)
var selectedPeerChats = mutableStateOf<Boolean>(true)
var showYourChatSearch = mutableStateOf(false)
var showDashfilterPersonal = mutableStateOf(false)
var showDashGroupfilter = mutableStateOf(false)
var showAutoMatch = mutableStateOf(false)
var showOverlayNameProfile = mutableStateOf(false)
var showOverlayBioProfile = mutableStateOf(false)
var showOverlayStuFiProfile = mutableStateOf(false)
var showOverlayExpProfile = mutableStateOf(false)
var showBottomSheet = mutableStateOf(false)
val listOfMajors = listOf<String>("Agriculture, and Related Sciences","Architecture and Related Services","Area, Ethnic, Cultural, Gender, and Group Studies","Aviation","Biological and Biomedical Sciences","Business, Management, and Related Support Services","Communication, Journalism, and Related Programs","Communications Technologies/technicians","Computer and Information Sciences","Construction Trades","Engineering","English Language and Literature/letters","Family and Consumer Sciences/human Sciences","Foreign Languages","Health Professions","History","Human Services","Mathematics and Statistics","Mechanic and Repair Technologies/technicians","Military Technologies and Applied Sciences","Natural Resources and Conservation","Culinary Services","Philosophy and Religious Studies","Physical Sciences","Psychology","Social Sciences","Theology and Religious Vocations","Transportation and Materials Moving","Visual and Performing Arts","Others")
