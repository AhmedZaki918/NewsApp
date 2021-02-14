package com.example.newsapp.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.database.ArticleDatabase
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistRepo(val application: Application) {

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
                ArticleDatabase(application).getArticleDao().deleteArticle(article)
                // Update list after deleting
                updateArticles()
            }
        }
        return articleLiveData
    }


    // Update the articles list to display it
    private suspend fun updateArticles() {
        articleList = ArticleDatabase(application).getArticleDao().getAllArticles()
        withContext(Dispatchers.Main)
        {
            articleLiveData.value = articleList
        }
    }
}