package com.example.postgram.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.postgram.data.UserData
import com.example.postgram.presentation.SharedViewModel
import com.example.postgram.presentation.components.PostItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OthersScreen(
    user: UserData,
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel()
) {

    val postData = viewModel.postData.value
    val isLoading = viewModel.isUserDataLoading.value

    if (isLoading) {
        MyProgressBar()
    }

    LaunchedEffect(key1 = true) {
        viewModel.getPosts("${user.userId}", specificPosts = true)
        viewModel.getUserData("${user.userId}")
    }

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.whiteAlpha))
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 55.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                        data = user.image ?: "https://shorturl.at/jmoHM"
                    ),
                    contentDescription = ""
                )
                Column {
                    Text(text = "@${user.username}")
                    Row {
                        Text(text = "${user.name} ")
                        Text(text = "${user.surname}")
                    }
                    Text(text = "${user.bio}")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.blue)
                    ),
                    onClick = {
                        navigateTo(
                            navController,
                            DestinationScreen.MessageScreen.route,
                            NavParam("userData", user)
                        )
                    }
                ) {
                    Text(text = "Message", color = Color.White)
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