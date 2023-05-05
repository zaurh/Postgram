package com.example.postgram.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.postgram.data.PostData

@Composable
fun PostItem(
    postData: PostData,
    onItemClick: () -> Unit
) {
    Box(modifier = Modifier
        .size(150.dp)
        .clickable {
            onItemClick()
        })
    {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            painter = rememberImagePainter(data = postData.image),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}