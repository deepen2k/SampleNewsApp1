package com.deepen.newsapp.network

import com.deepen.newsapp.data.models.NewsDataModel
import com.deepen.newsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPIService {
    @GET("v2/everything?sources=bbc-news&apiKey=${Constants.apiKey}")
    suspend fun fetchLatestNews(
        @Query("page") page: Int = 1,
    ): NewsDataModel

    @GET("v2/top-headlines")
    suspend fun fetchCategoryNews(
        @Query("category") category: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = Constants.apiKey
    ): NewsDataModel

}

