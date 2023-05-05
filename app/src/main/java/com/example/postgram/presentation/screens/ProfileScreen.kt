package com.example.postgram.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.postgram.R
import com.example.postgram.common.DestinationScreen
import com.example.postgram.common.MyProgressBar
import com.example.postgram.common.NavParam
import com.example.postgram.common.navigateTo
import com.example.postgram.presentation.SharedViewModel
import com.example.postgram.presentation.components.PostItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel()
) {


    SideEffect {
        val uid = viewModel.userData.value
        uid?.let {
            viewModel.getPosts(it.userId ?: "", specificPosts = true)
        }
    }

    val userData = viewModel.userData.value
    val postData = viewModel.postData.value
    val isLoading = viewModel.isUserDataLoading.value

    if (isLoading) {
        MyProgressBar()
    }


    userData?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.whiteAlpha))
                .padding(top = 20.dp, bottom = 55.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    painter = rememberImagePainter(
                        data = userData.image ?: "https://shorturl.at/jmoHM"
                    ),
                    contentDescription = ""
                )
                Column {
                    Text(text = "@${userData.username}")
                    Row {
                        Text(
                            text = if (userData.name?.isNotEmpty() == true) "${userData.name} " else "No name ",
                            color = if (userData.name?.isNotEmpty() == true) Color.Black else Color.LightGray
                        )
                        Text(text = if (userData.surname?.isNotEmpty() == true) "${userData.surname} " else "")
                    }
                    Text(
                        text = if (userData.bio?.isNotEmpty() == true) "${userData.bio} " else "",
                        maxLines = 3
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.blue)
                    ),
                    onClick = {
                        navController.navigate(DestinationScreen.Settings.route)
                    }
                ) {
                    Text(text = "Edit", color = Color.White)
                }


            }
            Divider(modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier.size(20.dp))
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(3),
                content = {
                    items(postData) { post ->
                        PostItem(postData = post, onItemClick = {
                            navigateTo(
                                navController,
                                DestinationScreen.SinglePostScreen.route,
                                NavParam("post", post)
                            )
                        })
                    }
                })
        }
    }
}



