package com.example.newsapp.data.database

import androidx.room.*
import com.example.newsapp.data.model.Article

@Dao
interface ArticleDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: Article)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT * FROM articles WHERE title = :title")
    suspend fun fetchInArticles(title: String): Article

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}