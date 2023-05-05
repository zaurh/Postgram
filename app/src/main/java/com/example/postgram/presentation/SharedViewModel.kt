package com.example.postgram.presentation

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.postgram.data.ForumData
import com.example.postgram.data.MessageData
import com.example.postgram.data.PostData
import com.example.postgram.data.UserData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    val showEmptyMessage = mutableStateOf(true)
    var isRefreshing = mutableStateOf(false)

    val userData = mutableStateOf<UserData?>(null)
    val usersData = mutableStateOf<List<UserData>>(listOf())

    val postData = mutableStateOf<List<PostData>>(listOf())
    val forumData = mutableStateOf<List<ForumData>>(listOf())
    val messageData = mutableStateOf<List<MessageData>>(listOf())

    val isLoading = mutableStateOf(false)
    val isSignedIn = mutableStateOf(false)
    val isUserDataLoading = mutableStateOf(false)


    init {

        isSignedIn.value = auth.currentUser != null
        val currentUserId = auth.currentUser?.uid
        currentUserId?.let {
            getUserData(it)
        }
        getAllUsers()
        getForumMessages()

    }


    //***********************  Firebase AUTH  ***********************


    fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        context: Context,
        username: String
    ) {
        isLoading.value = true
        if (!checkValidEmail(email)) {
            Toast.makeText(context, "Email is not valid.", Toast.LENGTH_SHORT).show()
            isLoading.value = false
        } else if (password.length < 8) {
            Toast.makeText(context, "Password should be at least 8 characters.", Toast.LENGTH_SHORT)
                .show()
            isLoading.value = false
        } else if (password != confirmPassword) {
            Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            isLoading.value = false
        } else {
            checkEmailExistence(email = email, context)
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    isLoading.value = false
                    isSignedIn.value = true
                    addUser(username)
                }
                .addOnFailureListener {
                    isLoading.value = false
                }
        }

    }

    fun signIn(email: String, password: String, context: Context) {
        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.currentUser?.let {
                    getUserData(it.uid)
                    isLoading.value = false
                    isSignedIn.value = true
                }

            }
            .addOnFailureListener {
                isLoading.value = false
                Toast.makeText(context, "Email or password is incorrect", Toast.LENGTH_SHORT).show()
            }
    }

    fun signOut() {
        forumData.value = listOf()
        auth.signOut()
        isSignedIn.value = false
        userData.value = null
        postData.value = listOf()
    }

    fun forgotPassword(email: String, context: Context) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            Toast.makeText(context, "Sent. Please check your email.", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(
                context,
                "Problem occurred. Please enter valid email.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //*****************   Firebase AUTH Catching errors   *********************

    private fun checkEmailExistence(email: String, context: Context) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result?.signInMethods?.isEmpty() == true) {
                        // Email does not exist
                    } else {
                        Toast.makeText(context, "Email is already registered.", Toast.LENGTH_SHORT)
                            .show()
                        isLoading.value = false
                    }
                } else {
                    // Error occurred
                }
            }
    }

    private fun checkValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }

    //***********************    Firebase Firestore   **********************

    private fun addUser(username: String) {
        val userId = auth.currentUser?.uid
        val userdata = UserData(
            username = username,
            userId = userId
        )
        userId?.let { uid ->
            fireStore.collection("user").document(uid).set(userdata)
                .addOnSuccessListener {
                    isLoading.value = false
                }
                .addOnFailureListener {
                    isLoading.value = false
                }
        }
    }


    fun getUserData(userId: String) {
        isUserDataLoading.value = true
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            fireStore.collection("user").document(userId).get()
                .addOnSuccessListener {
                    userData.value = it.toObject<UserData>()
                    isUserDataLoading.value = false
                }
        }
    }

    private fun getAllUsers() {
        isUserDataLoading.value = true
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            fireStore.collection("user")
                .addSnapshotListener { value, _ ->
                    value?.let {
                        usersData.value = it.toObjects()
                    }
                }
        }
    }


    fun updateUser(
        username: String? = null,
        name: String? = null,
        surname: String? = null,
        bio: String? = null,
        imageUrl: String? = null
    ) {
        val currentUserId = auth.currentUser?.uid
        val updateUserData = UserData(
            username = username ?: userData.value?.username,
            name = name ?: userData.value?.name,
            surname = surname ?: userData.value?.surname,
            bio = bio ?: userData.value?.bio,
            image = imageUrl ?: userData.value?.image
        )
        if (currentUserId != null) {
            fireStore.collection("user").document(currentUserId).update(updateUserData.toMap())
                .addOnSuccessListener {
                    this.userData.value = updateUserData
                }
            fireStore.collection("post")
                .whereEqualTo("userId", currentUserId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.update("user_username", username)
                        document.reference.update("user_name", name)
                        document.reference.update("user_surname", surname)
                        document.reference.update("userImage", imageUrl)
                        document.reference.update("userBio", bio)
                    }
                }
            fireStore.collection("forum")
                .whereEqualTo("senderUserId", currentUserId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.update("senderUsername", username)
                        document.reference.update("senderName", name)
                        document.reference.update("senderSurname", surname)
                        document.reference.update("senderUserImage", imageUrl)
                        document.reference.update("senderBio", bio)
                    }
                }

        }
    }


    fun addPost(uri: Uri, description: String, onSuccess: () -> Unit) {
        uploadingImage(uri) {
            creatingPost(description, it, onSuccess)
        }
    }

    private fun creatingPost(description: String, uri: Uri, onSuccess: () -> Unit) {
        isLoading.value = true
        val userId = auth.currentUser?.uid
        val username = userData.value?.username
        val randomId = UUID.randomUUID().toString()
        val postData = PostData(
            image = uri.toString(),
            userId = userId,
            user_username = username,
            userImage = userData.value?.image,
            user_name = userData.value?.name,
            userSurname = userData.value?.surname,
            userBio = userData.value?.bio,
            postId = randomId,
            time = Timestamp.now(),
            description = description
        )
        fireStore.collection("post").document(randomId).set(postData).addOnSuccessListener {
            isLoading.value = false
            onSuccess.invoke()
        }
    }


    fun getPosts(userId: String, specificPosts: Boolean) {
        isLoading.value = true
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            if (specificPosts) {
                fireStore.collection("post")
                    .whereEqualTo("userId", userId)
                    .addSnapshotListener { value, _ ->
                        value?.let { it ->
                            postData.value = it.toObjects<PostData>().sortedByDescending { it.time }
                        }
                        isLoading.value = false
                    }
            } else {
                fireStore.collection("post")
                    .addSnapshotListener { value, _ ->
                        value?.let { it ->
                            postData.value = it.toObjects<PostData>().sortedByDescending { it.time }
                        }
                        isLoading.value = false
                    }
            }

        }
    }

    //Uploading pictures

    fun uploadProfileImage(uri: Uri) {
        val user = userData.value
        user?.let {
            uploadingImage(uri) {
                updateUser(
                    username = user.username,
                    name = user.name,
                    surname = user.surname,
                    imageUrl = it.toString(),
                    bio = user.bio
                )

            }
        }

    }

    private fun uploadingImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        isLoading.value = true

        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)

        uploadTask
            .addOnSuccessListener {
                val result = it.metadata?.reference?.downloadUrl
                result?.addOnSuccessListener(onSuccess)
            }
            .addOnFailureListener {
                isLoading.value = false
            }
    }

    ////Forum

    fun addForumMessage(message: String) {
        sendingForumMessage(message)
    }

    private fun sendingForumMessage(message: String) {
        isLoading.value = true
        val userId = auth.currentUser?.uid
        val username = userData.value?.username
        val randomId = UUID.randomUUID().toString()
        val forumData = ForumData(
            forumMessage = message,
            forumId = randomId,
            senderUserId = userId,
            senderUserImage = userData.value?.image,
            senderUsername = username,
            senderName = userData.value?.name,
            senderSurname = userData.value?.surname,
            senderBio = userData.value?.bio
        )
        fireStore.collection("forum").document(randomId).set(forumData).addOnSuccessListener {
            isLoading.value = false
        }
    }

    private fun getForumMessages() {
        val currentUserId = auth.currentUser?.uid

        if (currentUserId != null) {
            fireStore.collection("forum")
                .addSnapshotListener { value, _ ->
                    value?.let { it ->
                        forumData.value = it.toObjects<ForumData>().sortedBy { it.time }
                    }
                }
        }
    }


    ////MESSENGER

    fun sendMessage(message: String, getterUsername: String, getterUserImage: String) {
        sendPrivateMessage(message = message, getterUsername, getterUserImage)
    }

    private fun sendPrivateMessage(
        message: String,
        getterUsername: String,
        getterUserImage: String
    ) {
        isLoading.value = true
        val userId = auth.currentUser?.uid
        val username = userData.value?.username
        val randomId = UUID.randomUUID().toString()
        val messageData = MessageData(
            message = message,
            messageId = randomId,
            senderUserId = userId,
            senderUserImage = userData.value?.image,
            senderUsername = username,
            senderName = userData.value?.name,
            senderSurname = userData.value?.surname,
            senderBio = userData.value?.bio,
            getterUsername = getterUsername,
            getterUserImage = getterUserImage
        )


        fireStore.collection("message").document(randomId).set(messageData).addOnSuccessListener {
            isLoading.value = false
        }


        fireStore.collection("user")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val sendMessagesTo = arrayListOf<String>()

                    userData.value?.sendMessagesTo?.let {
                        sendMessagesTo.addAll(it)
                    }

                    if (sendMessagesTo.contains(getterUsername)) {
                        //do nothing
                    } else {
                        sendMessagesTo.add(getterUsername)
                    }
                    document.reference.update("sendMessagesTo", sendMessagesTo)

                }
            }

        fireStore.collection("user")
            .whereEqualTo("username", getterUsername)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val gotMessagesFrom = arrayListOf<String>()

                    userData.value?.gotMessagesFrom?.let {
                        gotMessagesFrom.addAll(it)
                    }

                    if (gotMessagesFrom.contains(username)) {
                        //do nothing
                    } else {
                        gotMessagesFrom.add(username!!)
                    }
                    document.reference.update("gotMessagesFrom", gotMessagesFrom)
                }
            }


    }

    fun getPrivateMessages(getterUsername: String? = null) {
        val currentUserId = auth.currentUser?.uid
        val username = userData.value?.username

        if (currentUserId != null) {
            fireStore.collection("message")
                .whereIn("senderUsername", listOf(username, getterUsername))
                .whereIn("getterUsername", listOf(username, getterUsername))
                .addSnapshotListener { value, _ ->
                    value?.let { it ->
                        messageData.value = it.toObjects<MessageData>().sortedBy { it.time }
                    }
                }
        }
    }


    //Refresh Coins
    fun refreshCoinList() {
        postData.value = listOf()
        getPosts(specificPosts = false, userId = userData.value?.userId ?: "")
    }


}
