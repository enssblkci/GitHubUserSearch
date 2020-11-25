package com.ing.mobile.githubusersearch.model.search

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login")
    var userName: String?,
    @SerializedName("avatar_url")
    var avatarUrl: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(avatarUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }
}


