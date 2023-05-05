package com.example.homework4.data.remote

class NewsRepo(
    private val apiService: NewsApiService = RetrofitHelper.getInstance()
        .create(NewsApiService::class.java),
) {
    suspend fun fetchNews() = apiService.fetchNews()

    suspend fun searchNews(query: String, category: String? = null) = apiService.searchNews(query, category)
}
