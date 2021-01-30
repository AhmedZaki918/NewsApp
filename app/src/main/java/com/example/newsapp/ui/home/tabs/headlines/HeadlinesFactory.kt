package com.example.newsapp.ui.home.tabs.headlines

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.data.model.Article

class HeadlinesFactory : DataSource.Factory<Int, Article>() {

    val userLiveDataSource = MutableLiveData<HeadlinesRepo>()

    override fun create(): DataSource<Int, Article> {
        val newsDataSource =
            HeadlinesRepo()
        userLiveDataSource.postValue(newsDataSource)

        return newsDataSource
    }
}