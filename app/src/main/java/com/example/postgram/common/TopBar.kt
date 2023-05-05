package com.example.postgram.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postgram.R

@Composable
fun TopBar(
    title: String,
    addPhotoVisibility: Boolean,
    menuVisibility: Boolean,
    dotMenuVisibility: Boolean,
    chatVisibility: Boolean,
    addPhotoClick: () -> Unit,
    menuButtonClick: () -> Unit,
    chatClick: () -> Unit,
    dotMenuButtonClick: () -> Unit,
    dropdownExpanded: Boolean,
    onDismiss: () -> Unit,
    signOutClick: () -> Unit,
    menuSignOutVisibility: Boolean,
    settingsClick: () -> Unit,
    menuSettingsVisibility: Boolean,
    blockClick: () -> Unit,
    menuBlockVisibility: Boolean,
) {
    TopAppBar(

        backgroundColor = colorResource(id = R.color.blue),
        title = {
            Text(text = title, color = Color.White)
        }, actions = {
            if (chatVisibility) {
                IconButton(onClick = {
                    chatClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Forum,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            if (addPhotoVisibility) {
                IconButton(onClick = {
                    addPhotoClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            if (menuVisibility) {
                IconButton(onClick = {
                    menuButtonClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
            if (dotMenuVisibility) {
                IconButton(onClick = {
                    dotMenuButtonClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }


            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = onDismiss,
                modifier = Modifier
                    .width(160.dp)
            )
            {
                if (menuSignOutVisibility) {

                    Row(
                        Modifier
                            .fillMaxSize()
                            .clickable {
                                signOutClick()
                            }
                            .padding(
                                start = 15.dp,
                                end = 15.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            fontSize = 18.sp,
                            text = "Log out",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (menuSettingsVisibility) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .clickable {
                                settingsClick()
                            }
                            .padding(
                                start = 15.dp,
                                end = 15.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            fontSize = 18.sp,
                            text = "Settings",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (menuBlockVisibility){
                    Row(
                        Modifier
                            .fillMaxSize()
                            .clickable {
                                blockClick()
                            }
                            .padding(
                                start = 15.dp,
                                end = 15.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Block,
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            fontSize = 18.sp,
                            text = "Block",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }
        }

    )
}