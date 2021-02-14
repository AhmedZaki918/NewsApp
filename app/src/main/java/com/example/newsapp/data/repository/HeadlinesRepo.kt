package com.example.newsapp.data.repository

import android.app.Application
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.R
import com.example.newsapp.data.database.ArticleDatabase
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIClient
import com.example.newsapp.util.Constants
import com.example.newsapp.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeadlinesRepo(val application: Application?) : PageKeyedDataSource<Int, Article>() {
    constructor() : this(null)


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                APIClient.getApi.getHeadlines(
                    Constants.API_KEY, "en",
                    Constants.FIRST_PAGE + 1
                )

            if (response.isSuccessful) {
                val apiResponse = response.body()!!.articles
                apiResponse.let {
                    callback.onResult(apiResponse!!, null, Constants.FIRST_PAGE)
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
                    callback.onResult(apiResponse!!, key)
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
                    callback.onResult(apiResponse!!, key)
                }
            }
        }
    }


    // Receive data from database and notify the user data is saved
    fun sendResponse(article: Article?) {
        GlobalScope.launch(Dispatchers.IO) {
            ArticleDatabase(application!!).getArticleDao().addArticle(article!!)
            withContext(Dispatchers.Main) {
                application.toast(application.resources.getString(R.string.saved))
            }
        }
    }
}