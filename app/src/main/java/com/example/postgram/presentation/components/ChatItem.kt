package com.example.postgram.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.postgram.common.DestinationScreen
import com.example.postgram.common.NavParam
import com.example.postgram.common.navigateTo
import com.example.postgram.data.UserData
import com.example.postgram.presentation.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatItem(
    viewModel: SharedViewModel = hiltViewModel(),
    auth: FirebaseAuth = FirebaseAuth.getInstance(),
    navController: NavController,
    userData: UserData,

    ) {

    val showList = remember { mutableStateOf(false) }

    val admin = viewModel.userData.value






    if (userData.userId == auth.currentUser?.uid) {
        return
    } else if (
        admin?.gotMessagesFrom?.contains(userData.username) == true
    ) {
        showList.value = true
    } else if (admin?.sendMessagesTo?.contains(userData.username) == true) {
        showList.value = true
    }


    if (showList.value) {

        viewModel.showEmptyMessage.value = false
        Card(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                navigateTo(
                    navController,
                    DestinationScreen.MessageScreen.route,
                    NavParam("userData", userData)
                )
            }) {
            Row {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(CircleShape)
                        .size(70.dp),
                    painter = rememberImagePainter(
                        data = userData.image ?: "https://shorturl.at/jmoHM"
                    ),
                    contentDescription = ""
                )
                Column {
                    Text(text = "@${userData.username}")
                    Row {
                        Text(text = "${userData.name} ")
                        Text(text = "${userData.surname}")

                    }


                }

            }
        }


    }


}


