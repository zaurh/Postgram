package com.example.postgram.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class PostData(
    val postId: String? = null,
    val userId: String? = null,
    val user_username: String? = null,
    val userImage: String? = null,
    val user_name: String? = null,
    val userSurname: String? = null,
    val userBio: String? = null,
    val description: String? = null,
    val image: String? = null,
    val time: Timestamp = Timestamp.now()
): Parcelable {


    fun toUserData() =
        UserData(
            userId = userId,
            username = user_username,
            image = userImage,
            name = user_name,
            surname = userSurname,
            bio = userBio
        )

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(postId)
        parcel.writeString(userId)
        parcel.writeString(user_username)
        parcel.writeString(userImage)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeParcelable(time, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostData> {
        override fun createFromParcel(parcel: Parcel): PostData {
            return PostData(parcel)
        }

        override fun newArray(size: Int): Array<PostData?> {
            return arrayOfNulls(size)
        }
    }
}
