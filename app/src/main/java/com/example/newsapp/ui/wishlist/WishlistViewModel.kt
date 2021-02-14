package com.example.newsapp.ui.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.WishlistRepo

class WishlistViewModel(
    private val repo: WishlistRepo
) : ViewModel() {

    // Send request to repository to display data
    fun sendRequest(): MutableLiveData<List<Article>> {
        return repo.sendResponse()
    }

    // Send delete request from database
    fun sendDeleteRequest(article: Article?): MutableLiveData<List<Article>> {
        return repo.sendDeleteResponse(article)
    }
}