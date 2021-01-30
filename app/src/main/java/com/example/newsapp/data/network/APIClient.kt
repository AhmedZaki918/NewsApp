package com.example.newsapp.data.network

import com.example.newsapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getApi: APIInterface by lazy {
        retrofit.create(APIInterface::class.java)
    }
}