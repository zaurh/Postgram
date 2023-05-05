package com.example.postgram.data

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val userId: String? = null,
    val image: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val username: String? = null,
    val bio: String? = null,

    val sendMessagesTo: List<String> = emptyList(),
    val gotMessagesFrom: List<String> = emptyList()

) : Parcelable {

    fun toMap() = mapOf(
        "username" to username,
        "name" to name,
        "surname" to surname,
        "bio" to bio,
        "image" to image
    )

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(username)
        parcel.writeString(bio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }


}


