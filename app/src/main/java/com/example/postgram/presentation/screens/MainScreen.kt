package com.example.postgram.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postgram.R
import com.example.postgram.common.DestinationScreen
import com.example.postgram.common.NavParam
import com.example.postgram.common.navigateTo
import com.example.postgram.presentation.SharedViewModel
import com.example.postgram.presentation.components.FeedPostItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.isRefreshing.value
    )

    val allPosts = viewModel.postData.value
    val userData = viewModel.userData.value

    SideEffect {
        userData?.let {
            viewModel.getPosts(specificPosts = false, userId = it.userId ?: "")
        }
    }

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.whiteAlpha))
            .fillMaxSize()
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.refreshCoinList()
        }) {
            LazyColumn {
                items(allPosts) { post ->
                    FeedPostItem(
                        postData = post,
                        onUsernameClick = {
                            navigateTo(
                                navController,
                                if (post.user_username == viewModel.userData.value?.username) {
                                    DestinationScreen.Profile.route
                                } else {
                                    DestinationScreen.OthersScreen.route
                                },
                                NavParam("user", post.toUserData())
                            )
                        }, onImageClick = {
                            navigateTo(
                                navController,
                                DestinationScreen.SinglePostScreen.route,
                                NavParam("post", post)
                            )
                        })
                }
            }
        }
    }


}