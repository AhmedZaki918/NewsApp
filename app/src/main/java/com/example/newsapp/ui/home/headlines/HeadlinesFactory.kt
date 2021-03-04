package com.example.newsapp.ui.home.headlines

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.model.Article
import androidx.paging.DataSource
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.data.repository.HeadlinesRepo
import javax.inject.Inject

class HeadlinesFactory @Inject constructor(
    val articleDao: ArticleDao,
    private val apiInterface: APIInterface
) :
    DataSource.Factory<Int, Article>() {

     val userLiveDataSource = MutableLiveData<HeadlinesRepo>()


    override fun create(): DataSource<Int, Article> {
        val newsDataSource =
            HeadlinesRepo(articleDao, apiInterface)
        userLiveDataSource.postValue(newsDataSource)

        return newsDataSource
    }
}