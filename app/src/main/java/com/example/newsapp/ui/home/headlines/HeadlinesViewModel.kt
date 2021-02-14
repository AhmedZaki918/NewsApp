package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.HeadlinesRepo
import com.example.newsapp.util.Constants

class HeadlinesViewModel(
    private val repo: HeadlinesRepo
) :
    ViewModel() {

    val newsPagedList: LiveData<PagedList<Article>>
    private val liveDataSource: LiveData<HeadlinesRepo>

    init {
        val itemDataSourceFactory = HeadlinesDataSourceFactory()
        liveDataSource = itemDataSourceFactory.userLiveDataSource

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