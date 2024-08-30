package com.eww.dummyapicall.network

import com.eww.dummyapicall.model.User
import com.eww.dummyapicall.model.Users
import com.eww.dummyapicall.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    //-------DEMO-------//
    @GET(Constants.GET_USERS)
    suspend fun callDemoApi(): Response<List<User>>
}