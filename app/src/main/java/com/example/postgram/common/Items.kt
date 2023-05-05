package com.example.postgram.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.postgram.data.ForumData

@Composable
fun MessageImageIcon(
    forumData: ForumData
) {
    Image(
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(30.dp),
        painter = rememberImagePainter(data = forumData.senderUserImage ?: "https://shorturl.at/jmoHM"),
        contentDescription = ""
    )
}