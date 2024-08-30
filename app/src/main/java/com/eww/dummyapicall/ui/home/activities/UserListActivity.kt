package com.eww.dummyapicall.ui.home.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dummyapicall.R
import com.eww.dummyapicall.MyApplication
import com.eww.dummyapicall.model.User
import com.eww.dummyapicall.network.ApiResource
import com.eww.dummyapicall.ui.home.viewModel.UserListViewModel
import com.eww.dummyapicall.utils.isNetworkConnected

class UserListActivity : AppCompatActivity() {

    private val userListViewModel: UserListViewModel by viewModels {
        UserListViewModel.UserListViewModelFactory((application as MyApplication).repository)
    }

    private var tvMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        initView()
    }

    private fun initView() {
        setupObservers()
        tvMessage = findViewById(R.id.tvMessage)
        if (isNetworkConnected()) {
            userListViewModel.getUsersFromAPI()
        } else {
            userListViewModel.getUsersFromDB()
        }
    }

    private fun setupObservers() {
        userListViewModel.offlineUserListObserver.observe(this) { usersList ->
            usersList?.let { list ->
                Log.e("From DB", "setupObservers: offlineUserListObserver")
                setDataIntoList(list)
            }
        }

        userListViewModel.userListObserver.observe(this) { response ->
            when (response.status) {
                ApiResource.Status.SUCCESS -> {
                    Log.e("Activity", "setupObservers: SUCCESS")
                    val users = response.data
                    setDataIntoList(users ?: listOf())
                    userListViewModel.insert(users ?: listOf())
                }

                ApiResource.Status.ERROR -> {
                    Log.e("Activity", "setupObservers: ERROR : ${response.message}")
                }

                ApiResource.Status.LOADING -> {
                    Log.e("Activity", "setupObservers: LOADING")
                }

                else -> {
                    Log.e("Activity", "setupObservers: Other ERROR")
                }
            }
        }
    }

    private fun setDataIntoList(list: List<User>) {
        Log.e("Activity", "setDataIntoList: Got list of size : ${list.size}")
        var names = ""
        list.forEach {
            names = names.plus(it.name).plus("\n\n")
        }
        tvMessage?.text = names
    }
}