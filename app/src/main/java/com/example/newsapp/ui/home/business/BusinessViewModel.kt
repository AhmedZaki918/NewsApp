package com.example.newsapp.ui.home.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.BusinessRepo
import com.example.newsapp.util.Constants

class BusinessViewModel(
    private val repo: BusinessRepo
) : ViewModel() {

    val newsPagedList: LiveData<PagedList<Article>>
    private val liveRepo: LiveData<BusinessRepo>

    init {
        val itemDataSourceFactory = BusinessDataSourceFactory()
        liveRepo = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE)
            .build()

        newsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }


    // Send request to repository to save data
    fun sendRequest(article: Article?) {
        repo.sendResponse(article)
    }
}