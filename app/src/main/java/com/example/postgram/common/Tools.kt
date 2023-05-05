package com.example.postgram.common

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.postgram.presentation.SharedViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*


data class NavParam(
    val name: String,
    val value: Parcelable
)

fun navigateTo(navController: NavController, dest: String, vararg params: NavParam) {
    for (param in params) {
        navController.currentBackStackEntry?.arguments?.putParcelable(param.name, param.value)
    }
    navController.navigate(dest) {
        popUpTo(dest)
        launchSingleTop = true
    }
}

@Composable
fun MyCheckSignedIn(
    navController: NavController,
    viewModel: SharedViewModel
) {
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn = viewModel.isSignedIn.value
    if (signedIn && !alreadyLoggedIn.value) {
        alreadyLoggedIn.value = true
        navController.navigate("main") {
            popUpTo(0)
        }
    }
}

@Composable
fun MyProgressBar() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}





fun myFormatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd MMMM", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}

fun myTime(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("k:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}
