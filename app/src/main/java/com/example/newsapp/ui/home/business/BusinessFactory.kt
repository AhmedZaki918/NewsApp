package com.example.newsapp.ui.home.business

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.data.repository.BusinessRepo
import com.example.newsapp.data.repository.HeadlinesRepo
import javax.inject.Inject

class BusinessFactory @Inject constructor(
    val articleDao: ArticleDao,
    private val apiInterface: APIInterface
) :
    DataSource.Factory<Int, Article>() {

    val userLiveDataSource = MutableLiveData<BusinessRepo>()


    override fun create(): DataSource<Int, Article> {
        val newsDataSource =
            BusinessRepo(articleDao, apiInterface)
        userLiveDataSource.postValue(newsDataSource)

        return newsDataSource
    }
}
