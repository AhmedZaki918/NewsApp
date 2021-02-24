package com.example.newsapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepo @Inject constructor(
    private val apiInterface: APIInterface,
    private val articleDao: ArticleDao
) {

    // Initialization
    private val mutableLiveData = MutableLiveData<List<Article>>()
    private var articleList = listOf<Article>()


    // Get business data from api
    fun getBusiness(): MutableLiveData<List<Article>> {
        // Create get request on background thread
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                apiInterface.getBusiness(
                    Constants.API_KEY, Constants.LANGUAGE,
                    Constants.CATEGORY
                )
            if (response.isSuccessful) {
                articleList = response.body()!!.articles!!
                mutableLiveData.postValue(articleList)
            }
        }
        return mutableLiveData
    }


    // Save an article to database
    fun sendResponse(article: Article?) {
        GlobalScope.launch(Dispatchers.IO) {
            articleDao.addArticle(article!!)
        }
    }
}