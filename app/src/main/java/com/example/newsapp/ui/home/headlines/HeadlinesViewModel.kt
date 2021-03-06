package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val repo: HeadlinesRepo,
    private val articleDao: ArticleDao,
    api: APIInterface
) :
    ViewModel() {

    val articles = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
        HeadlinesRepo(articleDao, api)
    }.flow.cachedIn(viewModelScope)


    // Send request to repository to save data
    fun saveArticle(article: Article?) {
        repo.sendAdd(article)
    }

    // Delete request to repository
    fun deleteRequest(article: Article?) {
        repo.sendDelete(article)
    }
}