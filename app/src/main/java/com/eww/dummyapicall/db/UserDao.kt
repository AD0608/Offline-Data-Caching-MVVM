package com.eww.dummyapicall.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eww.dummyapicall.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(user: List<User>)

    @Query("delete from user")
    fun deleteAllUsers()

    @Query("select * from user")
    fun getAllUsers(): List<User>
}