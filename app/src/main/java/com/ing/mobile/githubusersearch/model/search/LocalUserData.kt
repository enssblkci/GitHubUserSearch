package com.ing.mobile.githubusersearch.model.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class LocalUserData(
    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0,
    @ColumnInfo(name = "repoID")
    var repoID: Int = 0,
    @ColumnInfo(name = "userName")
    var userName: String,
    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String,
    @ColumnInfo(name = "starCount")
    var starCount: Int?,
    @ColumnInfo(name = "repoName")
    var repoName: String?,
    @ColumnInfo(name = "open_issues_count")
    var openIssuesCount: Int?,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)