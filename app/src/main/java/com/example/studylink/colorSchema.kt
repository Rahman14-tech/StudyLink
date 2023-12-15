package com.example.studylink

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

var background: Color = Color(0xfff1f1f1)
var defaultColor: Color = Color(0xffffffff)
var headText: Color = Color(0xff202020)
var subheadText: Color = Color(0xff444444)
var placeholderColor: Color = Color(0xff767676)
var leftChatColor: Color = Color(0xffffffff)
var dividerColor: Color = Color(0xff959595)
var cardsColor: Color = Color(0xffffffff)
var groupButtonColor: Color = Color(0xff1c1a22)

var isDark = mutableStateOf(false)
var schemaCond = mutableStateOf("Light")

fun coloringSchema() {
    if (isDark.value) {
        schemaCond.value = "Dark"
        background = Color(0xff15141C)
        defaultColor = Color(0xff0A090F)
        headText = Color(0xffffffff)
        subheadText = Color(0xffBABABA)
        placeholderColor = Color(0xff7E7E7E)
        leftChatColor = Color(0xff1C1C24)
        dividerColor = Color(0xffBABABA)
        cardsColor = Color(0xff2C2B33)
        groupButtonColor = Color(0xffffc600)
    } else {
        schemaCond.value = "Light"
        background = Color(0xfff1f1f1)
        defaultColor = Color(0xffffffff)
        headText = Color(0xff202020)
        subheadText = Color(0xff444444)
        placeholderColor = Color(0xff767676)
        leftChatColor = Color(0xffffffff)
        dividerColor = Color(0xff959595)
        cardsColor = Color(0xffffffff)
        groupButtonColor = Color(0xff1c1a22)
    }
}