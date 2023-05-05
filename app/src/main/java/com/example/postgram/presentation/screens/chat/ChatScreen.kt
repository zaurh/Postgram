package com.example.postgram.presentation.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postgram.presentation.SharedViewModel
import com.example.postgram.presentation.components.ChatItem

@Composable
fun ChatScreen(
    viewModel: SharedViewModel = hiltViewModel(),
    navController: NavController
) {

    val user = viewModel.usersData.value
    val showEmptyMessage = viewModel.showEmptyMessage.value

    if (showEmptyMessage){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(modifier = Modifier.size(100.dp),imageVector = Icons.Default.ChatBubbleOutline, contentDescription = "")
            Text(text = "Inbox is empty.")
            Text(text = "Try messaging someone!")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn {
            items(user){
                ChatItem(
                    userData = it,
                    navController = navController)
            }
        }

    }

}