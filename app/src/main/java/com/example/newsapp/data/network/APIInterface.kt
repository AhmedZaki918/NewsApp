package com.example.newsapp.data.network

import com.example.newsapp.data.model.MainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("top-headlines")
   suspend fun getHeadlines(
        @Query("apiKey") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MainResponse>


    @GET("top-headlines")
    suspend fun getBusiness(
        @Query("apiKey") key: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): Response<MainResponse>
}