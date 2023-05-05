package com.example.postgram.presentation.screens.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postgram.R
import com.example.postgram.common.MyProgressBar
import com.example.postgram.presentation.SharedViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {

    val isLoading = sharedViewModel.isLoading.value
    val focus = LocalFocusManager.current
    var emailTf by remember { mutableStateOf("") }
    var emailTfError by remember { mutableStateOf(false) }

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            )

    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Forgot Password",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                text = "Enter the email associated with your account and we'll send an email with instructions to reset your password",
                color = Color.Gray,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (emailTfError) Color.Red else Color.DarkGray,
                        CircleShape
                    ),
                value = emailTf,
                onValueChange = { emailTf = it },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "",
                        tint = Color.DarkGray
                    )
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color.Gray,
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                keyboardActions = KeyboardActions(onDone = {
                    if (emailTf.isEmpty()) {
                        emailTfError = true
                    } else {
                        sharedViewModel.forgotPassword(emailTf, context)
                        focus.clearFocus()
                    }
                }),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    if (emailTfError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                }
            )
            if (emailTfError) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Please enter email.",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.size(30.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.blue)
                ),
                onClick = {
                    if (emailTf.isEmpty()) {
                        emailTfError = true
                    } else {
                        sharedViewModel.forgotPassword(emailTf, context)
                        focus.clearFocus()
                    }
                }) {
                Text(
                    text = "Send",
                    color = Color.White,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }

            Spacer(modifier = Modifier.size(32.dp))
            Row {
                Text(text = "Go back to ", color = Color.Black)
                Text(
                    text = "Sign in.",
                    textDecoration = TextDecoration.Underline,
                    color = colorResource(id = R.color.blue),
                    modifier = Modifier.clickable {
                        navController.navigate("sign_in") {
                            popUpTo(0)
                        }
                    })
            }

        }

        if (isLoading) {
            MyProgressBar()
        }
    }

}