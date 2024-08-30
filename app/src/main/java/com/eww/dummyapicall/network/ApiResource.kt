package com.eww.dummyapicall.network

data class ApiResource<out T>(
    val status: Status,
    val data: T?,
    val message: String? = "",
    val code: Int = 200
) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        NO_INTERNET_CONNECTION,
        UNKNOWN
    }

    companion object {

        fun <T> success(data: T, message: String? = ""): ApiResource<T> {
            return ApiResource(Status.SUCCESS, data, message)
        }

        fun <T> error(message: String?, data: T? = null, code: Int = 200): ApiResource<T> {
            return ApiResource(Status.ERROR, data, message, code)
        }

        fun <T> loading(data: T? = null): ApiResource<T> {
            return ApiResource(Status.LOADING, data, null)
        }

        fun <T> unknown(data: T? = null): ApiResource<T> {
            return ApiResource(Status.UNKNOWN, data, null)
        }

        fun <T> noInternetConnection(): ApiResource<T> {
            return ApiResource(Status.NO_INTERNET_CONNECTION, null)
        }
    }
}
