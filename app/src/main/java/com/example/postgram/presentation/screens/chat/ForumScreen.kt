package com.example.postgram.presentation.screens.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postgram.R
import com.example.postgram.common.DestinationScreen
import com.example.postgram.common.NavParam
import com.example.postgram.common.navigateTo
import com.example.postgram.data.UserData
import com.example.postgram.presentation.SharedViewModel
import com.example.postgram.presentation.components.ForumMessageItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForumScreen(
    navController: NavController, viewModel: SharedViewModel = hiltViewModel(), userData: UserData
) {
    var messageTf by remember { mutableStateOf("") }
    val messages = viewModel.forumData.value
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()


    SideEffect {
        scope.launch {
            scrollState.scrollToItem(messages.size)
        }
    }
    Scaffold(

        content = {

            Column(
                modifier = Modifier.padding(bottom = 60.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(state = scrollState) {
                    items(messages.takeLast(30)) { message ->
                        ForumMessageItem(forumData = message) {
                            if (message.senderUserId == userData.userId) {
                                return@ForumMessageItem
                            } else {
                                navigateTo(
                                    navController,
                                    DestinationScreen.OthersScreen.route,
                                    NavParam("user", message.toUserData())
                                )
                            }


                        }
                    }

                }


            }


        }, bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp, bottom = 5.dp)
            ) {
                TextField(textStyle = TextStyle(
                    fontSize = 18.sp,
                ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f)
                        .clip(CircleShape),
                    colors = TextFieldDefaults.textFieldColors(
                        placeholderColor = colorResource(id = R.color.whiteAlpha),
                        focusedIndicatorColor = Color.Transparent,
                        backgroundColor = colorResource(id = R.color.blue),
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                        textColor = Color.White,
                        cursorColor = Color.White
                    ),
                    placeholder = { Text(text = "Message") },
                    value = messageTf,
                    onValueChange = { messageTf = it },
                    trailingIcon = {
                        IconButton(modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(CircleShape),
                            onClick = {
                                if (messageTf.trim().isNotEmpty()) {
                                    scope.launch {
                                        if (messages.isNotEmpty()) {
                                            scrollState.scrollToItem(messages.size - 1)
                                        }
                                    }
                                    viewModel.addForumMessage(messageTf.trimStart().trimEnd())
                                    messageTf = ""
                                }

                            }) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    })
            }
        })


}