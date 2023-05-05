package com.example.postgram.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.postgram.R
import com.example.postgram.data.PostData

@Composable
fun FeedPostItem(
    postData: PostData,
    onUsernameClick: () -> Unit,
    onImageClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.whiteAlpha))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onUsernameClick()
                    }) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    painter = rememberImagePainter(data = postData.userImage ?: "https://shorturl.at/jmoHM"),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = postData.user_username ?: "")

            }
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clickable {
                        onImageClick()
                    },
                painter = rememberImagePainter(data = postData.image),
                contentDescription = ""
            )
        }

    }
}