package com.example.newsapp.ui.home.business

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.BusinessRepo

class BusinessDataSourceFactory : DataSource.Factory<Int, Article>() {

    val userLiveDataSource = MutableLiveData<BusinessRepo>()

    override fun create(): DataSource<Int, Article> {
        val businessDataSource =
            BusinessRepo()
        userLiveDataSource.postValue(businessDataSource)

        return businessDataSource
    }
}