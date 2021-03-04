package com.example.newsapp.data.repository

import androidx.paging.PageKeyedDataSource
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.ArticlesResponse
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepo @Inject constructor(
    private val articleDao: ArticleDao,
    private val apiInterface: APIInterface

) : PageKeyedDataSource<Int, Article>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = createRequest(Constants.FIRST_PAGE + 1)
            if (response.isSuccessful) {
                callback.onResult(
                    response.body()!!.articles!!,
                    null, Constants.FIRST_PAGE
                )
            }
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = createRequest(params.key)
            if (response.isSuccessful) {
                callback.onResult(
                    response.body()!!.articles!!,
                    if (params.key > 1) params.key - 1 else 0
                )
            }
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = createRequest(params.key)
            if (response.isSuccessful) {
                callback.onResult(
                    response.body()!!.articles!!,
                    params.key + 1
                )
            }
        }
    }


    // Add one article to database
    fun sendResponse(article: Article?) {
        GlobalScope.launch(Dispatchers.IO) {
            articleDao.addArticle(article!!)
        }
    }

    // Delete one item from database
    fun sendDelete(article: Article?) {
        GlobalScope.launch(Dispatchers.IO) {
            articleDao.deleteArticle(article!!)
        }
    }

    // Get request from api
    private suspend fun createRequest(page: Int): Response<ArticlesResponse> {
        return apiInterface.getBusiness(
            Constants.API_KEY,
            Constants.LANGUAGE,
            Constants.CATEGORY,
            page
        )
    }
}