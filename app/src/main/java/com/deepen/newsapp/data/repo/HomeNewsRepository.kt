package com.deepen.newsapp.data.repo

import com.deepen.newsapp.data.models.NewsDataModel
import com.deepen.newsapp.network.NewsAPIService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HomeNewsRepository {
    suspend fun getLatestNews(): NewsDataModel
    suspend fun getCategoryNews(category: String): NewsDataModel
    fun getPeriodicNews(): Flow<NewsDataModel>
}

class NetworkHomeNewsRepository(
    private val newsApiService: NewsAPIService,
    private val refreshIntervalMs: Long = 10000
) : HomeNewsRepository {
    private var pageCounter = 1;
    override suspend fun getLatestNews(): NewsDataModel {
        return newsApiService.fetchLatestNews()
    }

    override suspend fun getCategoryNews(category: String): NewsDataModel {
        return newsApiService.fetchCategoryNews(category)
    }

    override fun getPeriodicNews(): Flow<NewsDataModel> = flow {
        while(true) {
            val latestNews = newsApiService.fetchLatestNews(page = pageCounter)
            emit(latestNews) // Emits the result of the request to the flow
            delay(refreshIntervalMs) // Suspends the coroutine for some time
            pageCounter++;
        }
    }

}