package com.example.postgram.presentation.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.postgram.R
import com.example.postgram.common.MyProgressBar
import com.example.postgram.presentation.SharedViewModel

@Composable
fun AddPostScreen(
    navController: NavController,
    viewModel: SharedViewModel = hiltViewModel(),
    encodedUri: String
) {
    val isLoading = viewModel.isLoading.value
    var postDescription by remember { mutableStateOf("") }
    val imageUri by remember { mutableStateOf(encodedUri) }
    val focus = LocalFocusManager.current


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp, start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.End
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop,
                painter = rememberImagePainter(data = imageUri),
                contentDescription = ""
            )
            Divider(Modifier.padding(top = 5.dp, bottom = 5.dp))

            OutlinedTextField(
                placeholder = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                value = postDescription,
                onValueChange = { postDescription = it }
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.blue)
                ),
                onClick = {
                    focus.clearFocus()
                    viewModel.addPost(description = postDescription, uri = Uri.parse(imageUri)) {
                        navController.popBackStack()
                    }
                }) {
                Text(text = "Share", fontSize = 16.sp, color = Color.White)
            }
        }
    }
    if (isLoading) {
        MyProgressBar()
    }
}