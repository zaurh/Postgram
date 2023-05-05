package com.example.postgram.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postgram.R
import com.example.postgram.data.UserData
import com.example.postgram.presentation.SharedViewModel

@Composable
fun BottomBar(
    navController: NavController,
    viewModel: SharedViewModel
) {
    val selectedIndex = remember { mutableStateOf(0) }

    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            modifier = Modifier.background(colorResource(id = R.color.blue)),
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            selected = (selectedIndex.value == 0),
            onClick = {
                navController.navigate("main"){
                    popUpTo(0)
                }
                selectedIndex.value = 0
            })

        BottomNavigationItem(
            modifier = Modifier.background(colorResource(id = R.color.blue)),
            icon = {
                Column {
                    Icon(imageVector = Icons.Default.Message, "")
                }

            },
            selected = (selectedIndex.value == 1),
            onClick = {
                val userD = viewModel.userData.value
                navigateTo(
                    navController,
                    DestinationScreen.ForumScreen.route,
                    NavParam("userData", userD ?: UserData())
                )
                selectedIndex.value = 1

            })

        BottomNavigationItem(
            modifier = Modifier.background(colorResource(id = R.color.blue)),
            icon = {
                Icon(imageVector = Icons.Default.Person, "")
            },
            selected = (selectedIndex.value == 2),
            onClick = {
                    navController.navigate("profile"){
                        popUpTo(0)
                    }
                selectedIndex.value = 2
            })
    }
}