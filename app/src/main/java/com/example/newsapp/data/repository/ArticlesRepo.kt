package com.example.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.database.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.ArticlesResponse
import com.example.newsapp.data.network.APIInterface
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Coroutines
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ArticlesRepo @Inject constructor(
    private val articleDao: ArticleDao,
    private val api: APIInterface,
    private val category: String
) : PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = createRequest(nextPageNumber)
            LoadResult.Page(
                data = response.articles!!,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.totalResults) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int {
        return 0
    }


    // Check category type and return required api response
    private suspend fun createRequest(page: Int): ArticlesResponse {
        Constants.apply {
            return if (category == HEAD_LINES) {
                api.getHeadlines(API_KEY, LANGUAGE, page)
            } else {
                api.getBusiness(API_KEY, LANGUAGE, CATEGORY, page)
            }
        }
    }


    // Add an article to database or remove it
    fun addOrRemove(article: Article?, operation: String) {
        Coroutines.background {
            if (operation == "save") {
                articleDao.addArticle(article!!)
            } else {
                articleDao.deleteArticle(article!!)
            }
        }
    }
}