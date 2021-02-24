package com.example.newsapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishlistRepo @Inject constructor(
    private val articleDao: ArticleDao
) {

    //Initialization
    private var articleList = listOf<Article>()
    private val articleLiveData = MutableLiveData<List<Article>>()


    // Receive data from database and update ui
    fun sendResponse(): MutableLiveData<List<Article>> {
        GlobalScope.launch(Dispatchers.IO) {
            updateArticles()
        }
        return articleLiveData
    }


    // Delete one item from database
    fun sendDeleteResponse(article: Article?): MutableLiveData<List<Article>> {
        GlobalScope.launch(Dispatchers.IO) {
            if (article != null) {
                articleDao.deleteArticle(article)
                // Update list after deleting
                updateArticles()
            }
        }
        return articleLiveData
    }


    // Update the articles list to display it
    private suspend fun updateArticles() {
        articleList = articleDao.getAllArticles()
        articleLiveData.postValue(articleList)
    }
}