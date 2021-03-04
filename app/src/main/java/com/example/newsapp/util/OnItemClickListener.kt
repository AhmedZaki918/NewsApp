package com.example.newsapp.util

import com.example.newsapp.data.model.Article
import dagger.Provides
import javax.inject.Inject

interface OnItemClickListener {
    fun onItemClick(article: Article?)
    fun onSave(article: Article?)
    fun onDelete(article: Article?)
}