package com.example.newsapp.ui.home.tabs.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.Constants

class BusinessViewModel : ViewModel() {

    val newsPagedList: LiveData<PagedList<Article>>
    private val liveRepo: LiveData<BusinessRepo>

    init {
        val itemDataSourceFactory = BusinessFactory()

        liveRepo = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE)
            .build()

        newsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }

}