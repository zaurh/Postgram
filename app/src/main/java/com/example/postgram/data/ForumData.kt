package com.example.postgram.data

import com.google.firebase.Timestamp


data class ForumData(
    val forumId: String? = null,
    val forumMessage: String? = null,
    val senderName: String? = null,
    val senderSurname: String? = null,
    val senderBio: String? = null,
    val senderUserId: String? = null,
    val senderUsername: String? = null,
    val senderUserImage: String? = null,
    val time: Timestamp? = Timestamp.now()
) {
    fun toUserData() =
        UserData(
            userId = senderUserId,
            username = senderUsername,
            image = senderUserImage,
            name = senderName,
            surname = senderSurname,
            bio = senderBio
        )
}
