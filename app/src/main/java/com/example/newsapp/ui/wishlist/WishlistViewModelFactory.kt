package com.example.newsapp.ui.wishlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.WishlistRepo

@Suppress("UNCHECKED_CAST")
class WishlistViewModelFactory(private val repo: WishlistRepo) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WishlistViewModel(repo) as T
    }
}