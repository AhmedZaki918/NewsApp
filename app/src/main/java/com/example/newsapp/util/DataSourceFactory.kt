//package com.example.newsapp.util
//
//import androidx.lifecycle.MutableLiveData
//import com.example.newsapp.data.model.Article
//import androidx.paging.DataSource
//import com.example.newsapp.data.database.ArticleDao
//import com.example.newsapp.data.network.APIInterface
//import com.example.newsapp.ui.home.headlines.ArticlesRepo
//import javax.inject.Inject
//
//class DataSourceFactory @Inject constructor(
//    val articleDao: ArticleDao,
//    private val apiInterface: APIInterface,
//    private var category: String
//) :
//    DataSource.Factory<Int, Article>() {
//
//    val userLiveDataSource = MutableLiveData<ArticlesRepo>()
//
//
//    override fun create(): DataSource<Int, Article> {
//        val newsDataSource =
//            ArticlesRepo(articleDao, apiInterface, category)
//        userLiveDataSource.postValue(newsDataSource)
//        return newsDataSource
//    }
//}