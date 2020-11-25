package com.ing.mobile.githubusersearch.utils

import android.content.Context
import com.ing.mobile.githubusersearch.data.local.UsersDatabase
import com.ing.mobile.githubusersearch.model.search.LocalUserData

class DataBaseHelper {
    companion object{
        val shared = DataBaseHelper()
    }
    fun getFavoriteUserList(context: Context):List<LocalUserData> {
        val db = UsersDatabase.getInstance(context)
        return db.usersDao().getFavoriteRepoList()
    }
    fun insertFavoriteRepo(context: Context, userData: LocalUserData) {
        val db = UsersDatabase.getInstance(context)
        db.usersDao().insertUsers(userData)
    }
    fun deleteFavoriteRepo(context: Context, ID: Int) {
        val db = UsersDatabase.getInstance(context)
        db.usersDao().deleteUser(ID)
    }
}