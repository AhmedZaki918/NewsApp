package com.example.newsapp.ui.home.headlines

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.HeadlinesRepo

@Suppress("UNCHECKED_CAST")
class HeadlinesViewModelFactory(
    private val repo: HeadlinesRepo
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeadlinesViewModel(repo) as T
    }
}