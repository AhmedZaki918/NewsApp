package com.example.newsapp.ui.home.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.data.repository.BusinessRepo
import com.example.newsapp.data.repository.HeadlinesRepo
import com.example.newsapp.ui.home.headlines.HeadlinesFactory
import com.example.newsapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val repo: BusinessRepo,
    articleDao: ArticleDao,
    apiInterface: APIInterface
) :
    ViewModel() {


    val newsPagedList: LiveData<PagedList<Article>>
    private val liveRepo: LiveData<BusinessRepo>

    init {
        val itemDataSourceFactory =
            BusinessFactory(articleDao, apiInterface)
        liveRepo = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE)
            .build()

        newsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }


    // Send request to repository to save data
    fun saveArticle(article: Article?) {
        repo.sendResponse(article)
    }


    // Delete request to repository
    fun deleteRequest(article: Article?) {
        repo.sendDelete(article)
    }
}