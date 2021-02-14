package com.example.newsapp.util

import com.example.newsapp.data.model.Article

interface OnItemClickListener {
    fun onItemClick(article: Article?)
    fun saveItem(article: Article?)
}