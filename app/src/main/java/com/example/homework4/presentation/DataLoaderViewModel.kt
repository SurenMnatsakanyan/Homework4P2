package com.example.homework4.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework4.data.UIArticle
import com.example.homework4.data.remote.NewsRepo
import com.example.homework4.data.toUIArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataLoaderViewModel(
    private val repository: NewsRepo = NewsRepo(),
) : ViewModel() {
    private val _articles: MutableLiveData<List<UIArticle>> = MutableLiveData(emptyList())
    val articles: LiveData<List<UIArticle>> = _articles

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadNews() {
        viewModelScope.launch {
            val articles = repository.fetchNews().articles
            withContext(Dispatchers.Main) {
                articles?.run {
                    _articles.postValue(articles.map { it.toUIArticle() })
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun searchNews(query: String, category: String? = null) {
        viewModelScope.launch {
            val articles = repository.searchNews(query, category).articles
            withContext(Dispatchers.Main) {
                articles?.run {
                    _articles.postValue(articles.map { it.toUIArticle() })
                }
            }
        }
    }
}
