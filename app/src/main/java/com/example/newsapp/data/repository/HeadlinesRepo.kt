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
class HeadlinesRepo @Inject constructor(
    private val articleDao: ArticleDao,
    private val apiInterface: APIInterface
) {

    //Initialization
    private val mutableLiveData = MutableLiveData<List<Article>>()
    private var articleList = listOf<Article>()


    // Get headlines articles via api
    fun getData(): MutableLiveData<List<Article>> {
        // Create get request on background thread
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                apiInterface.getHeadlines(
                    Constants.API_KEY, Constants.LANGUAGE
                )
            if (response.isSuccessful) {
                articleList = response.body()!!.articles!!
                mutableLiveData.postValue(articleList)
            }
        }
        return mutableLiveData
    }


    // Receive data from database and notify the user data is saved
    fun sendResponse(article: Article?) {
        GlobalScope.launch(Dispatchers.IO) {
            articleDao.addArticle(article!!)
        }
    }


    // Delete one item from database
    fun sendDeleteResponse(article: Article?) {
        GlobalScope.launch(Dispatchers.IO) {
            articleDao.deleteArticle(article!!)
        }
    }
}