package com.example.postgram.common

sealed class DestinationScreen(val route: String) {
    object Main : DestinationScreen("main")
    object SignIn : DestinationScreen("sign_in")
    object SplashScreen : DestinationScreen("splash_screen")
    object SignUp : DestinationScreen("sign_up")
    object Profile : DestinationScreen("profile")
    object OthersScreen : DestinationScreen("others_screen")
    object ForumScreen : DestinationScreen("forum_screen")
    object MessageScreen : DestinationScreen("message_screen")
    object ChatScreen : DestinationScreen("chat_screen")
    object SinglePostScreen : DestinationScreen("single_post_screen")
    object ForgotPassword : DestinationScreen("forgot_password")
    object Settings : DestinationScreen("settings")
    object AddPost : DestinationScreen("add_post/{imageUri}") {
        fun createRoute(uri: String) = "add_post/$uri"
    }
}