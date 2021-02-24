package com.example.newsapp.ui.home.business

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.BusinessRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val repo: BusinessRepo
) : ViewModel() {


    // Send request to repository to displays articles
    fun getData(): MutableLiveData<List<Article>> {
        return repo.getBusiness()
    }


    // Send request to repository to save data
    fun saveArticle(article: Article?) {
        repo.sendResponse(article)
    }
}