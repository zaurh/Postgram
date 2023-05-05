package com.example.postgram

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.postgram.common.*
import com.example.postgram.data.PostData
import com.example.postgram.data.UserData
import com.example.postgram.presentation.SharedViewModel
import com.example.postgram.presentation.screens.*
import com.example.postgram.presentation.screens.auth.ForgotPasswordScreen
import com.example.postgram.presentation.screens.auth.SignInScreen
import com.example.postgram.presentation.screens.auth.SignUpScreen
import com.example.postgram.presentation.screens.chat.ChatScreen
import com.example.postgram.presentation.screens.chat.ForumScreen
import com.example.postgram.presentation.screens.chat.PrivateMessageScreen
import com.example.postgram.ui.theme.PostgramTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostgramTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()

                    var topBarVisible by remember { mutableStateOf(false) }
                    var bottomBarVisible by remember { mutableStateOf(false) }

                    var inSettings by remember { mutableStateOf(false) }
                    var inAddPost by remember { mutableStateOf(false) }
                    var inProfile by remember { mutableStateOf(false) }
                    var inSearch by remember { mutableStateOf(false) }
                    var inSinglePost by remember { mutableStateOf(false) }
                    var inForum by remember { mutableStateOf(false) }
                    var inMessage by remember { mutableStateOf(false) }

                    var addPhotoVisibility by remember { mutableStateOf(true) }
                    var menuVisibility by remember { mutableStateOf(true) }
                    var dotMenuVisibility by remember { mutableStateOf(false) }
                    var chatVisibility by remember { mutableStateOf(false) }

                    var menuSignOutVisibility by remember { mutableStateOf(true) }
                    var menuSettingsVisibility by remember { mutableStateOf(true) }
                    var menuBlockVisibility by remember { mutableStateOf(false) }


                    val newPostImageLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri ->
                        uri?.let {
                            val encoded = Uri.encode(it.toString())
                            val route = DestinationScreen.AddPost.createRoute(encoded)
                            navController.navigate(route)
                        }
                    }


                    val viewModel: SharedViewModel = hiltViewModel()
                    var menuDropdownExpanded by remember { mutableStateOf(false) }


                    @RequiresApi(Build.VERSION_CODES.P)
                    @Composable
                    fun MyNav() {
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = DestinationScreen.SplashScreen.route
                        ) {
                            composable(DestinationScreen.SplashScreen.route) {
                                SplashScreen(navController = navController)
                            }
                            composable(DestinationScreen.Main.route,
                                exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -300 },
                                    animationSpec = tween(
                                        durationMillis = 600,
                                        easing = FastOutSlowInEasing
                                    )
                                ) +
                                        fadeOut(animationSpec = tween(600))
                            },
                                popEnterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { -300 },
                                        animationSpec = tween(
                                            durationMillis = 600,
                                            easing = FastOutSlowInEasing
                                        )
                                    ) + fadeIn(animationSpec = tween(600))
                                }, enterTransition = {
                                    slideInHorizontally(
                                        initialOffsetX = { -300 },
                                        animationSpec = tween(
                                            durationMillis = 600,
                                            easing = FastOutSlowInEasing
                                        )
                                    ) +
                                            fadeIn(animationSpec = tween(600))
                                }) {
                                inMessage = false
                                chatVisibility = true
                                inForum = false
                                menuBlockVisibility = false
                                menuSignOutVisibility = true
                                menuSettingsVisibility = true
                                dotMenuVisibility = false
                                addPhotoVisibility = true
                                menuVisibility = true
                                inSearch = false
                                inProfile = false
                                bottomBarVisible = true
                                topBarVisible = true
                                MainScreen(navController = navController)
                            }
                            composable(DestinationScreen.SignUp.route) {
                                bottomBarVisible = false
                                topBarVisible = false
                                SignUpScreen(navController = navController)
                            }
                            composable(DestinationScreen.SignIn.route) {
                                bottomBarVisible = false
                                topBarVisible = false
                                SignInScreen(navController = navController)
                            }
                            composable(DestinationScreen.ForgotPassword.route) {
                                bottomBarVisible = false
                                topBarVisible = false
                                ForgotPasswordScreen(navController = navController)
                            }
                            composable(
                                DestinationScreen.Profile.route,
                                popEnterTransition = {
                                    EnterTransition.None
                                },
                                enterTransition = {
                                    EnterTransition.None
                                }
                            ) {
                                chatVisibility = true
                                inForum = false
                                menuBlockVisibility = false
                                menuSignOutVisibility = true
                                menuSettingsVisibility = true
                                dotMenuVisibility = false
                                inSearch = false
                                inProfile = true
                                addPhotoVisibility = true
                                menuVisibility = true
                                inAddPost = false
                                inSettings = false
                                bottomBarVisible = true
                                topBarVisible = true
                                ProfileScreen(navController = navController)
                            }


                            composable(DestinationScreen.OthersScreen.route) {
                                chatVisibility = true
                                inMessage = false
                                inForum = false
                                menuBlockVisibility = true
                                menuSignOutVisibility = false
                                menuSettingsVisibility = false
                                dotMenuVisibility = true
                                inSearch = false
                                inProfile = false
                                addPhotoVisibility = false
                                menuVisibility = false
                                inAddPost = false
                                inSettings = false
                                bottomBarVisible = true
                                topBarVisible = true
                                val userData = navController
                                    .previousBackStackEntry
                                    ?.arguments
                                    ?.getParcelable<UserData>("user")
                                userData?.let {
                                    OthersScreen(
                                        navController = navController,
                                        user = userData
                                    )
                                }
                            }

                            composable(
                                DestinationScreen.SinglePostScreen.route
                            ) {
                                chatVisibility = false
                                inSinglePost = true
                                addPhotoVisibility = false
                                menuVisibility = false
                                val postData = navController
                                    .previousBackStackEntry
                                    ?.arguments
                                    ?.getParcelable<PostData>("post")

                                postData?.let {
                                    SinglePostScreen(
                                        postData = postData,
                                        navController = navController
                                    )
                                }
                            }

                            composable(DestinationScreen.ForumScreen.route) {
                                chatVisibility = false
                                inForum = true
                                inProfile = false
                                addPhotoVisibility = false
                                dotMenuVisibility = false
                                menuVisibility = false
                                inSettings = false
                                bottomBarVisible = false
                                topBarVisible = true

                                val userData = navController
                                    .previousBackStackEntry
                                    ?.arguments
                                    ?.getParcelable<UserData>("userData")

                                userData?.let {
                                    ForumScreen(
                                        navController = navController,
                                        userData = userData
                                    )
                                }
                            }

                            composable(DestinationScreen.MessageScreen.route,

                                exitTransition = {
                                    ExitTransition.None
                                },) {
                                chatVisibility = false
                                inMessage = true
                                inForum = false
                                inProfile = false
                                addPhotoVisibility = false
                                dotMenuVisibility = false
                                menuVisibility = false
                                inSettings = false
                                bottomBarVisible = false
                                topBarVisible = true

                                val userData = navController
                                    .previousBackStackEntry
                                    ?.arguments
                                    ?.getParcelable<UserData>("userData")

                                userData?.let {
                                    PrivateMessageScreen(
                                        navController = navController,
                                        userData = userData
                                    )
                                }
                            }

                            composable(DestinationScreen.ChatScreen.route,


                                exitTransition = {
                                    ExitTransition.None
                                },) {
                                chatVisibility = false
                                inMessage = true
                                inForum = false
                                inProfile = false
                                addPhotoVisibility = false
                                dotMenuVisibility = false
                                menuVisibility = false
                                inSettings = false
                                bottomBarVisible = false
                                topBarVisible = true

                                val userData = navController
                                    .previousBackStackEntry
                                    ?.arguments
                                    ?.getParcelable<UserData>("messageData")

                                userData?.let {
                                    ChatScreen(
                                        navController = navController,
                                    )
                                }
                            }

                            composable(DestinationScreen.AddPost.route) { navBackStackEntry ->
                                chatVisibility = false
                                menuVisibility = false
                                addPhotoVisibility = false
                                inAddPost = true
                                bottomBarVisible = false
                                topBarVisible = true
                                val imageUri = navBackStackEntry.arguments?.getString("imageUri")
                                imageUri?.let {
                                    AddPostScreen(navController = navController, encodedUri = it)
                                }
                            }
                            composable(DestinationScreen.Settings.route) {
                                chatVisibility = false
                                addPhotoVisibility = false
                                menuVisibility = false
                                inSettings = true
                                bottomBarVisible = false
                                topBarVisible = true
                                SettingsScreen(navController = navController)
                            }


                        }

                    }
                    Scaffold(
                        topBar = {
                            if (topBarVisible) {
                                TopBar(

                                    title =
                                    if (inSettings) "Settings"
                                    else if (inAddPost) "Add Post"
                                    else if (inProfile) "Profile"
                                    else if (inSearch) "Search"
                                    else if (inForum) "Global Chat"
                                    else if (inMessage) "Messenger"
                                    else "Postgram",
                                    chatVisibility = chatVisibility,
                                    addPhotoVisibility = addPhotoVisibility,
                                    menuVisibility = menuVisibility,
                                    chatClick = {
                                        val userD = viewModel.userData.value

                                        navigateTo(
                                            navController,
                                            DestinationScreen.ChatScreen.route,
                                            NavParam("messageData", userD ?: UserData())
                                        )
                                    },
                                    addPhotoClick = {
                                        newPostImageLauncher.launch("image/*")
                                    },
                                    menuButtonClick = {
                                        menuDropdownExpanded = true
                                    },
                                    dropdownExpanded = menuDropdownExpanded,
                                    onDismiss = {
                                        menuDropdownExpanded = false
                                    },
                                    signOutClick = {
                                        viewModel.signOut()
                                        navController.navigate("sign_in") {
                                            popUpTo(0)
                                        }
                                        menuDropdownExpanded = false
                                    },
                                    menuSignOutVisibility = menuSignOutVisibility,
                                    settingsClick = {
                                        navController.navigate("settings")
                                        menuDropdownExpanded = false
                                    },
                                    menuSettingsVisibility = menuSettingsVisibility,
                                    blockClick = {},
                                    menuBlockVisibility = menuBlockVisibility,
                                    dotMenuVisibility = dotMenuVisibility,
                                    dotMenuButtonClick = {
                                        menuDropdownExpanded = true
                                    }
                                )
                            }
                        },
                        content = {
                            MyNav()
                        },
                        bottomBar = {
                            if (bottomBarVisible) {
                                BottomBar(navController = navController, viewModel)
                            }
                        }
                    )
                }
            }
        }
    }
}



