package com.ing.mobile.githubusersearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ing.mobile.githubusersearch.model.search.LocalUserData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(posts: LocalUserData)

    @Query("SELECT * FROM users")
    fun getFavoriteRepoList(): List<LocalUserData>

    @Query("DELETE FROM users WHERE repoID = :ID")
    fun deleteUser(ID: Int)

}