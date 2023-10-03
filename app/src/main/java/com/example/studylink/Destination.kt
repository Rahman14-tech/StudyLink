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
    override val icon: Int = R.drawable.home
    override val title: String = "Dashboard"
}
