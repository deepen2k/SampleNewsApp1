package com.deepen.newsapp.fake.home

import com.deepen.newsapp.data.models.NewsDataModel
import com.deepen.newsapp.data.repo.HomeNewsRepository

class FakeNetworkHomeNewsRepository:HomeNewsRepository {
    override suspend fun getLatestNews(): NewsDataModel {
       return FakeDataSource.articleList
    }

    override suspend fun getCategoryNews(category: String): NewsDataModel {
        return FakeDataSource.articleList
    }
}