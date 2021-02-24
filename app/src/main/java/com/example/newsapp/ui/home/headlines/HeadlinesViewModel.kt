package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.HeadlinesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val repo: HeadlinesRepo
) :
    ViewModel() {


    // Send request to repository to display articles
    fun getArticles(): MutableLiveData<List<Article>> {
        return repo.getData()
    }


    // Send request to repository to save data
    fun saveArticle(article: Article?) {
        repo.sendResponse(article)
    }

    // Delete request to repository
    fun deleteRequest(article: Article?) {
        repo.sendDeleteResponse(article)
    }
}