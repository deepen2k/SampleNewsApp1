package com.deepen.newsapp.fake.room

import com.deepen.newsapp.data.models.Article
import com.deepen.newsapp.fake.home.FakeDataSource
import com.loc.newsapp.data.local.NewsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeNewsDao :NewsDao {
    override suspend fun upsert(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(article: Article) {
        TODO("Not yet implemented")
    }

    override  fun getArticles(): Flow<List<Article>>  = flow {
        FakeDataSource.articleList.articles
    }

    override suspend fun getArticle(url: String): Article {
        TODO("Not yet implemented")
    }

}