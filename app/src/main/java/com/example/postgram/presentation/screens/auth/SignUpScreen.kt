package com.example.postgram.presentation.screens.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.postgram.R
import com.example.postgram.common.MyCheckSignedIn
import com.example.postgram.common.MyProgressBar
import com.example.postgram.presentation.SharedViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {

    MyCheckSignedIn(navController = navController, viewModel = sharedViewModel)

    val context = LocalContext.current
    val isLoading = sharedViewModel.isLoading.value
    val focus = LocalFocusManager.current

    var usernameTf by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }

    var emailTf by remember { mutableStateOf("") }
    var emailTfError by remember { mutableStateOf(false) }

    var passwordTf by remember { mutableStateOf("") }
    var passwordTfError by remember { mutableStateOf(false) }

    var confirmPasswordTf by remember { mutableStateOf("") }
    var confirmPasswordTfError by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
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
                text = "Sign up",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(40.dp))
            TextField(
                keyboardActions = KeyboardActions(
                    onDone = {
                        focus.clearFocus()
                    }
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = if (usernameError) Color.Red else Color.DarkGray,
                        CircleShape
                    ),
                value = usernameTf,
                onValueChange = { usernameTf = it },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        tint = Color.DarkGray
                    )
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Username",
                        color = Color.Gray,
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    if (usernameError)
                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                }
            )
            if (usernameError) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Please enter username.",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                keyboardActions = KeyboardActions(
                    onDone = {
                        focus.clearFocus()
                    }
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .fillMaxWidth()
                    .border(
                        1.dp,
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
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
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
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                keyboardActions = KeyboardActions(
                    onDone = {
                        focus.clearFocus()
                    }
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = if (passwordTfError) Color.Red else Color.DarkGray,
                        CircleShape
                    ),
                value = passwordTf,
                onValueChange = { passwordTf = it },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "",
                        tint = Color.DarkGray
                    )
                },
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color.Gray,
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (passwordTfError)
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector =
                                if (passwordVisibility)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = "",
                                tint = Color.DarkGray
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            if (passwordTfError) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Please enter password.",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = if (confirmPasswordTfError) Color.Red else Color.DarkGray,
                        CircleShape
                    ),
                value = confirmPasswordTf,
                onValueChange = { confirmPasswordTf = it },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                keyboardActions = KeyboardActions(onDone = {
                    usernameError = usernameTf.isEmpty()
                    emailTfError = emailTf.isEmpty()
                    passwordTfError = passwordTf.isEmpty()
                    confirmPasswordTfError = confirmPasswordTf.isEmpty()
                    if (!usernameError && !emailTfError && !passwordTfError && !confirmPasswordTfError){
                        sharedViewModel.signUp(
                            emailTf,
                            passwordTf,
                            confirmPasswordTf,
                            context,
                            usernameTf
                        )
                    }
                    focus.clearFocus()
                }),
                singleLine = true,
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "",
                        tint = Color.DarkGray
                    )
                },
                placeholder = {
                    Text(
                        text = "Confirm password",
                        color = Color.Gray,
                        modifier = Modifier.alpha(0.5f)
                    )
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (confirmPasswordTfError)
                            Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                        IconButton(onClick = {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        }) {
                            Icon(
                                imageVector =
                                if (confirmPasswordVisibility)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = "",
                                tint = Color.DarkGray
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            if (confirmPasswordTfError) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Please enter password again.",
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
                    usernameError = usernameTf.isEmpty()
                    emailTfError = emailTf.isEmpty()
                    passwordTfError = passwordTf.isEmpty()
                    confirmPasswordTfError = confirmPasswordTf.isEmpty()
                    if (!usernameError && !emailTfError && !passwordTfError && !confirmPasswordTfError){
                        sharedViewModel.signUp(
                            emailTf,
                            passwordTf,
                            confirmPasswordTf,
                            context,
                            usernameTf
                        )
                    }
                    focus.clearFocus()
                }) {
                Text(
                    text = "Sign up",
                    color = Color.White,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            Row {
                Text(text = "Already have an account? ", color = Color.DarkGray)
                Text(
                    text = "Sign In!",
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