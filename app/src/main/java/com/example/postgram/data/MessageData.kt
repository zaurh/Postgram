package com.example.postgram.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp


data class MessageData(
    val messageId: String? = null,
    val message: String? = null,
    val senderName: String? = null,
    val senderSurname: String? = null,
    val senderBio: String? = null,
    val senderUserId: String? = null,
    val senderUsername: String? = null,
    val senderUserImage: String? = null,
    val getterUsername: String? = null,
    val getterUserId: String? = null,
    val getterUserImage: String? = null,
    val time: Timestamp? = Timestamp.now()
):Parcelable {

    fun toUserData() =
        UserData(
            userId = senderUserId,
            username = senderUsername,
            image = senderUserImage,
            name = senderName,
            surname = senderSurname,
            bio = senderBio
        )


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(messageId)
        parcel.writeString(message)
        parcel.writeString(senderName)
        parcel.writeString(senderSurname)
        parcel.writeString(senderBio)
        parcel.writeString(senderUserId)
        parcel.writeString(senderUsername)
        parcel.writeString(senderUserImage)
        parcel.writeString(getterUsername)
        parcel.writeString(getterUserId)
        parcel.writeString(getterUserImage)
        parcel.writeParcelable(time, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageData> {
        override fun createFromParcel(parcel: Parcel): MessageData {
            return MessageData(parcel)
        }

        override fun newArray(size: Int): Array<MessageData?> {
            return arrayOfNulls(size)
        }
    }


}
