package com.example.newsapp.ui.home.tabs.business

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.data.model.Article

class BusinessFactory : DataSource.Factory<Int, Article>() {

    val userLiveDataSource = MutableLiveData<BusinessRepo>()

    override fun create(): DataSource<Int, Article> {
        val businessDataSource =
            BusinessRepo()
        userLiveDataSource.postValue(businessDataSource)

        return businessDataSource
    }
}