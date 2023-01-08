package com.example.checkintenet.data

import com.example.checkintenet.data.utils.RetrofitInstance

class Resposity {

    suspend fun  getUser()=RetrofitInstance.api.getUser()
}