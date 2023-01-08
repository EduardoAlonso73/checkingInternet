package com.example.checkintenet.data.utils

import com.example.checkintenet.data.api.ApiServer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClint())
            .build()
    }


    private fun getClint(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .build()

    }

    val api: ApiServer by lazy { retrofit.create(ApiServer::class.java) }


}