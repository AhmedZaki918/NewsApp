package com.example.newsapp.ui.home.tech

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.data.repository.ArticlesRepo
import com.example.newsapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TechViewModel @Inject constructor(
    private val repo: ArticlesRepo,
    private val articleDao: ArticleDao,
    api: APIInterface
) : ViewModel() {

    val articles = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
        ArticlesRepo(articleDao, api, "")
    }.flow.cachedIn(viewModelScope)


    // Send request to repository to save or remove data
    fun createOperation(article: Article?, operation: String) {
        repo.addOrRemove(article, operation)
    }
}