package com.example.newsapp.ui.home.business

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.BusinessRepo

@Suppress("UNCHECKED_CAST")
class BusinessViewModelFactory(private val repo: BusinessRepo) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BusinessViewModel(repo) as T
    }
}