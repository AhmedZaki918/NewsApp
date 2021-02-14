package com.example.newsapp.data.database

import androidx.room.TypeConverter
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {

    // Two converter methods for Source class
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}