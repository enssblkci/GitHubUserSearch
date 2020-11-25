package com.ing.mobile.githubusersearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ing.mobile.githubusersearch.model.search.LocalUserData
import com.ing.mobile.githubusersearch.model.search.SearchConstant.Bundle.TABLE_NAME

@Database(
    entities = [LocalUserData::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase(){

    abstract fun usersDao() : UserDao
    companion object{
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        /**
         * build our room DB
         */
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java, TABLE_NAME)
                .allowMainThreadQueries().build()
    }

}