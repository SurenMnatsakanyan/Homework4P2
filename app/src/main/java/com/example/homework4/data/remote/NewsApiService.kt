package com.example.homework4.data.remote

import com.example.homework4.data.Constants
import com.example.homework4.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET(Constants.VERSION + "top-headlines?apiKey=${Constants.API_KEY}&country=us")
    suspend fun fetchNews(): NewsResponse

    @GET(Constants.VERSION + "top-headlines?apiKey=${Constants.API_KEY}&country=us")
    suspend fun searchNews(
        @Query("q") query: String? = null,
        @Query("category") category: String? = null,
    ): NewsResponse
}
