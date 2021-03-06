package com.example.newsapp.ui.home.headlines

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Coroutines
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HeadlinesRepo @Inject constructor(
    private val articleDao: ArticleDao,
    private val api: APIInterface,
) : PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        Constants.apply {
            return try {
                val nextPageNumber = params.key ?: 0
                val response = api.getHeadlines(API_KEY, LANGUAGE, nextPageNumber)
                LoadResult.Page(
                    data = response.articles!!,
                    prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber < response.totalResults) nextPageNumber + 1 else null
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int {
        return 0
    }


    // Add one article to database
    fun sendAdd(article: Article?) {
        Coroutines.background {
            articleDao.addArticle(article!!)
        }
    }

    // Delete one item from database
    fun sendDelete(article: Article?) {
        Coroutines.background {
            articleDao.deleteArticle(article!!)
        }
    }
}