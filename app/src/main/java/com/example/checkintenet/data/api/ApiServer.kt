package com.example.checkintenet.data.api

import com.example.checkintenet.data.model.PostEnt
import retrofit2.Response
import retrofit2.http.GET

interface ApiServer {

    @GET("posts/1")
    suspend fun getUser(): Response<PostEnt>
}