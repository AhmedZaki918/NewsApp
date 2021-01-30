package com.example.newsapp.ui.home.tabs.headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.Constants

class HeadlinesViewModel : ViewModel() {

    val newsPagedList: LiveData<PagedList<Article>>
    private val liveDataSource: LiveData<HeadlinesRepo>

    init {
        val itemDataSourceFactory = HeadlinesFactory()
        liveDataSource = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE)
            .build()

        newsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
}