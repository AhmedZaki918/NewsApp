package com.example.newsapp.ui.home.tabs.headlines

import androidx.paging.PageKeyedDataSource
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIClient
import com.example.newsapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HeadlinesRepo : PageKeyedDataSource<Int, Article>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                APIClient.getApi.getHeadlines(
                    Constants.API_KEY, "en",
                    Constants.FIRST_PAGE
                )

            if (response.isSuccessful) {
                val apiResponse = response.body()!!.articles
                apiResponse.let {
                    callback.onResult(apiResponse, null, Constants.FIRST_PAGE + 1)
                }
            }
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                APIClient.getApi.getHeadlines(
                    Constants.API_KEY, "en",
                    params.key
                )

            if (response.isSuccessful) {
                val apiResponse = response.body()!!.articles
                val key = params.key + 1
                apiResponse.let {
                    callback.onResult(apiResponse, key)
                }
            }
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                APIClient.getApi.getHeadlines(
                    Constants.API_KEY, "en",
                    params.key
                )

            if (response.isSuccessful) {
                val apiResponse = response.body()!!.articles
                val key = if (params.key > 1) params.key - 1 else 0
                apiResponse.let {
                    callback.onResult(apiResponse, key)
                }
            }
        }
    }
}