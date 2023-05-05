package com.example.postgram.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postgram.R
import com.example.postgram.common.MessageImageIcon
import com.example.postgram.common.myTime
import com.example.postgram.data.ForumData
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForumMessageItem(
    auth: FirebaseAuth = FirebaseAuth.getInstance(),
    forumData: ForumData,
    onClick: () -> Unit,
    ) {
    val senderUid = forumData.senderUserId
    val currentUid = auth.currentUser?.uid

    var senderSide by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (senderUid == currentUid) {
            senderSide = true
        }
    }
    Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.whiteAlpha))
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(10.dp),
            horizontalAlignment = if (senderUid == currentUid) Alignment.End else Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (senderSide) {
                    Text(
                        text = forumData.senderUsername ?: "",
                        fontSize = 14.sp,
                        color = colorResource(
                            id = R.color.background
                        )
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    MessageImageIcon(forumData = forumData)
                } else {
                    MessageImageIcon(forumData = forumData)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = forumData.senderUsername ?: "",
                        fontSize = 14.sp,
                        color = colorResource(
                            id = R.color.background
                        )
                    )
                }

            }

            Column(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (senderUid == currentUid) colorResource(id = R.color.green) else colorResource(
                            id = R.color.blueStatus
                        )
                    )
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
                horizontalAlignment = if (senderUid == currentUid) Alignment.End else Alignment.Start
            ) {
                Text(text = forumData.forumMessage ?: "", color = Color.White)

            }
            Text(
                text = myTime(forumData.time!!),
                fontSize = 12.sp,
                color = colorResource(id = R.color.background)
            )

        }


    }
}

