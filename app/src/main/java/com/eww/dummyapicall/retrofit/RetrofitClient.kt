package com.eww.dummyapicall.retrofit

import com.eww.dummyapicall.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object RetrofitClient {
    private lateinit var retrofit: Retrofit

    fun getClient(): Retrofit {
        val levelType: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        client.addInterceptor(logging)
        try {
            client.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {

                    try {
                        val original: Request = chain.request()
                        val requestBuilder = original.newBuilder()
                        /*requestBuilder.addHeader(
                            Constants.API_KEY,
                            Constants.API_KEY_VALUE
                        )*/
                        val request = requestBuilder.build()
                        return chain.proceed(request)


                    } catch (e: Exception) {
                        val original: Request = chain.request()
                        val msg = "Server side error occurred"

                        return Response.Builder()
                            .request(original)
                            .protocol(Protocol.HTTP_1_1)
                            .code(999)
                            .message(msg)
                            .body("{${e}}".toResponseBody(null)).build()
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit
    }
}