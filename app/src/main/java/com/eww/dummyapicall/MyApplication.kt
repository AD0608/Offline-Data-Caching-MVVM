package com.eww.dummyapicall

import android.app.Application
import com.eww.dummyapicall.db.UserDatabase
import com.eww.dummyapicall.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    private val database by lazy { UserDatabase.getInstance(this) }
    val repository by lazy { UserRepository(database.userDao()) }
}