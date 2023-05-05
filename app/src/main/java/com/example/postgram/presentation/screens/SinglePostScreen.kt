package com.example.postgram.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.postgram.common.DestinationScreen
import com.example.postgram.common.NavParam
import com.example.postgram.common.myFormatDate
import com.example.postgram.common.navigateTo
import com.example.postgram.data.PostData
import com.example.postgram.presentation.SharedViewModel

@Composable
fun SinglePostScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel(),
    postData: PostData
) {

    val userData = viewModel.userData.value

    postData.userId?.let {

        Column(
            Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(),
                contentScale = ContentScale.Inside,
                painter = rememberImagePainter(data = postData.image),
                contentDescription = ""
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text =
                    if (postData.description?.isNotEmpty() == true)
                        postData.description else "No description",
                    color = if (postData.description?.isEmpty() == true) Color.LightGray else Color.Black
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        userData?.let {
                            navigateTo(
                                navController,
                                if (postData.user_username == userData.username) {
                                    DestinationScreen.Profile.route
                                } else {
                                    DestinationScreen.OthersScreen.route
                                },
                                NavParam("user", postData.toUserData())
                            )
                        }
                    }
                ) {
                    Text(text = postData.user_username ?: "username", textAlign = TextAlign.End)
                    Spacer(modifier = Modifier.size(5.dp))
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        painter = rememberImagePainter(
                            data = postData.userImage ?: "https://shorturl.at/jmoHM"
                        ),
                        contentDescription = ""
                    )
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Text(text = myFormatDate(postData.time), color = Color.Black)

            }

        }

    }
}
