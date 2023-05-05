package com.example.postgram.presentation.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.postgram.R
import com.example.postgram.common.MyProgressBar
import com.example.postgram.presentation.SharedViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController, viewModel: SharedViewModel = hiltViewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.uploadProfileImage(uri)
        }
    }

    val userData = viewModel.userData.value
    val isLoading = viewModel.isUserDataLoading.value
    val focus = LocalFocusManager.current

    var usernameTfError by remember { mutableStateOf(false) }


    if (isLoading) {
        MyProgressBar()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            userData?.let {
                var usernameTf by remember { mutableStateOf(userData.username ?: "") }
                var nameTf by remember { mutableStateOf(userData.name ?: "") }
                var surnameTf by remember { mutableStateOf(userData.surname ?: "") }
                var bioTf by remember { mutableStateOf(userData.bio ?: "") }

                Box(modifier = Modifier.clickable {
                    launcher.launch("image/*")
                }) {
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                        painter = rememberImagePainter(
                            data = userData.image ?: "https://shorturl.at/jmoHM"
                        ),
                        contentDescription = ""
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .align(
                                Alignment.BottomEnd
                            )
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))

                TextField(
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                        }
                    ),
                    trailingIcon = {
                        if (usernameTfError)
                            Icon(Icons.Filled.Error, "error", tint = colors.error)
                    },
                    singleLine = true,
                    maxLines = 1,
                    placeholder = { Text(text = "Username") },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = if (usernameTfError) colors.error else Color.Gray,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.blue),
                    ),
                    value = usernameTf,
                    onValueChange = { usernameTf = it },

                    )

                TextField(
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                        }
                    ),
                    singleLine = true,
                    maxLines = 1,
                    placeholder = { Text(text = "Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.blue),
                    ),
                    value = nameTf,
                    onValueChange = { nameTf = it })

                TextField(
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                        }
                    ),
                    singleLine = true,
                    maxLines = 1,
                    placeholder = { Text(text = "Surname") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.blue),
                    ),
                    value = surnameTf,
                    onValueChange = { surnameTf = it })

                TextField(
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focus.clearFocus()
                        }
                    ),
                    maxLines = 3,
                    placeholder = { Text(text = "Bio") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.blue),
                    ),
                    value = bioTf,
                    onValueChange = { bioTf = it })

                Spacer(modifier = Modifier.size(30.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.blue)
                    ),
                    onClick = {
                        focus.clearFocus()
                        usernameTfError = usernameTf.isEmpty()
                        if (usernameTf.isNotEmpty()) {
                            viewModel.updateUser(
                                usernameTf, nameTf, surnameTf, bioTf, imageUrl = userData.image
                            )
                            navController.navigate("profile") {
                                popUpTo(0)
                            }
                        }

                    }) {
                    Text(text = "Save", color = Color.White)
                }
            }
        }
    }
}