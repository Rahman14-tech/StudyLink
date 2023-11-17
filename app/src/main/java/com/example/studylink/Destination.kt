package com.example.studylink

interface Destinations{
    val route: String
    val icon: Int
    val title: String
}

object Login: Destinations{
    override val route: String = "Login"
    override val icon: Int = R.drawable.home
    override val title: String = "Login"
}

object Register: Destinations{
    override val route: String = "Register"
    override val icon: Int = R.drawable.home
    override val title: String = "Register"
}

object Dashboard: Destinations{
    override val route: String = "Dashboard"
    override val icon: Int = R.drawable.searchfriend
    override val title: String = "Find Friend"
}

object YourChats: Destinations{
    override val route: String = "Chats"
    override val icon: Int = R.drawable.chaticon
    override val title: String = "Chats"
}

object QNA: Destinations{
    override val route: String = "Forum"
    override val icon: Int = R.drawable.qna
    override val title: String = "Forum"
}

object Setting: Destinations{
    override val route: String = "Setting"
    override val icon: Int = R.drawable.anprofile
    override val title: String = "Setting"
}

object TheChatS: Destinations{
    override val route: String = "ChatSystem"
    override val icon: Int = R.drawable.chaticon
    override val title: String = "Current Chat"
    const val ChatId = "ChatId"

}

