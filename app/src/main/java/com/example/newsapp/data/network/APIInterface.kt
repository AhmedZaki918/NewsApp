package com.example.newsapp.data.network

import com.example.newsapp.data.model.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("apiKey") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ArticlesResponse


    @GET("top-headlines")
    suspend fun getBusiness(
        @Query("apiKey") key: String,
        @Query("language") language: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): ArticlesResponse
}