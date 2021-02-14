package com.example.newsapp.ui.wishlist

import com.example.newsapp.data.model.Article

interface WishlistListener {
    fun onDelete(article: Article?)
    fun onShare(article: Article?)
    fun onItemClick(article: Article?)
}