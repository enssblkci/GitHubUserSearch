package com.ing.mobile.githubusersearch.extension

import android.content.Context
import com.ing.mobile.githubusersearch.utils.DataBaseHelper

fun isFavoriteRepo(context: Context, repoId: Int): Boolean {
    val localFavoriteUser = DataBaseHelper.shared.getFavoriteUserList(context)
    val result = localFavoriteUser.filter { user -> user.repoID == repoId }
    return result.isNotEmpty()
}