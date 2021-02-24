package com.example.newsapp.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.data.model.Article

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "Your Api Key"
    const val PAGE_SIZE = 10
    const val FIRST_PAGE = 1
    const val MODEL = "ARTICLE_MODEL"
    const val LANGUAGE = "en"
    const val CATEGORY = "business"

     // For paging library
     val USER_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.source?.id == newItem.source?.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            newItem == oldItem
    }
}