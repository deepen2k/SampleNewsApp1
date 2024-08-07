package com.deepen.newsapp.fake.home

import com.deepen.newsapp.data.models.NewsDataModel
import com.deepen.newsapp.network.NewsAPIService

class FakeNewsApiService : NewsAPIService{
    override suspend fun getLatestNews(): NewsDataModel {
        return FakeDataSource.articleList
    }

    override suspend fun fetchCategoryNews(
        category: String,
        page: Int,
        apiKey: String
    ): NewsDataModel {
        return FakeDataSource.articleList
    }

//    override suspend fun getLatestNews(): NewsDataModel {
//        return FakeDataSource.articleList
//    }

}