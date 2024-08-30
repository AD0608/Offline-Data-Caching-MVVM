package com.eww.dummyapicall.repository

import androidx.annotation.WorkerThread
import com.eww.dummyapicall.db.UserDao
import com.eww.dummyapicall.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun insert(users: List<User>) {
        userDao.insertAllUsers(users)
    }

    @WorkerThread
    suspend fun getAllUsers() : List<User>{
        return userDao.getAllUsers()
    }

    @WorkerThread
    suspend fun deleteAll(){
        userDao.deleteAllUsers()
    }
}