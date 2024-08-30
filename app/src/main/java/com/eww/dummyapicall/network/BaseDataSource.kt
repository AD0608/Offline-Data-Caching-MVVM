package com.eww.dummyapicall.network

import com.eww.dummyapicall.utils.Constants
import retrofit2.Response

open class BaseDataSource {
    open suspend fun <T> getResult(call: () -> Response<T>): ApiResource<T> {

        val response = call()
        try {
            if (response.code() == Constants.SESSION_EXPIRE_CODE_401 ||
                response.code() == Constants.SESSION_EXPIRE_CODE_403
            ) {
                return ApiResource.error(response.message(), code = response.code())
            } else if (response.body() != null) {
                val baseResponse = (response.body() as ArrayList<*>)
                if (response.isSuccessful) {
                    response.body()?.let {
                        return ApiResource.success(it, "Success")
                    }
                } else {
                    val body = response.body()
                    if (body != null) {
                        return ApiResource.error("Error", code = response.code())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ApiResource.error(response.message(), code = response.code())
    }


}