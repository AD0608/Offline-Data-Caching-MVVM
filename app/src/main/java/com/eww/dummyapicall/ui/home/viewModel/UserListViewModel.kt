package com.eww.dummyapicall.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eww.dummyapicall.model.User
import com.eww.dummyapicall.model.Users
import com.eww.dummyapicall.network.ApiResource
import com.eww.dummyapicall.network.ApiService
import com.eww.dummyapicall.network.BaseDataSource
import com.eww.dummyapicall.repository.UserRepository
import com.eww.dummyapicall.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserListViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insert(users: List<User>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.insert(users)
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.deleteAll()
            }
        }
    }

    private val _userListOfflineResponse: MutableLiveData<List<User>> =
        MutableLiveData()
    val offlineUserListObserver: LiveData<List<User>> get() = _userListOfflineResponse

    fun getUsersFromDB() {
        var response: List<User>
        viewModelScope.launch {
            _userListResponse.value = ApiResource.loading(null)
            withContext(Dispatchers.IO) {
                response = userRepository.getAllUsers()
            }
            withContext(Dispatchers.Main) {
                response.run {
                    delay(500)
                    _userListOfflineResponse.value = this
                }
            }
        }
    }



    private val _userListResponse: MutableLiveData<ApiResource<List<User>>> =
        MutableLiveData()
    val userListObserver: LiveData<ApiResource<List<User>>> get() = _userListResponse

    fun getUsersFromAPI() {
        var response: Response<List<User>>
        val baseDataSource = BaseDataSource()
        viewModelScope.launch {
            _userListResponse.value = ApiResource.loading(null)
            withContext(Dispatchers.IO) {
                response = RetrofitClient.getClient().create(ApiService::class.java).callDemoApi()
            }
            withContext(Dispatchers.Main) {
                response.run {
                    delay(500)
                    _userListResponse.value = baseDataSource.getResult { this }
                }
            }
        }
    }

    class UserListViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserListViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}