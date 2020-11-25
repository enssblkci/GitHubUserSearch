package com.ing.mobile.githubusersearch.model.search

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GitHubUser(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("owner")
    var owner: Owner,
    @SerializedName("stargazers_count")
    var starCount: Int?,
    @SerializedName("name")
    var repoName: String?,
    @SerializedName("open_issues_count")
    var openIssuesCount: Int?,
    var isFavorite: Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Owner::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte()
    ) {}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeParcelable(owner, flags)
        parcel.writeValue(starCount)
        parcel.writeString(repoName)
        parcel.writeValue(openIssuesCount)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GitHubUser> {
        override fun createFromParcel(parcel: Parcel): GitHubUser {
            return GitHubUser(parcel)
        }
        override fun newArray(size: Int): Array<GitHubUser?> {
            return arrayOfNulls(size)
        }
    }
}