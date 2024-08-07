package com.deepen.newsapp.fake.room

import com.deepen.newsapp.data.repo.NewsDatabaseRepository
import com.deepen.newsapp.data.models.Article
import com.deepen.newsapp.fake.home.FakeDataSource
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow


class FakeBookmarkNewsDatabaseRepository : NewsDatabaseRepository {
    override fun getArticles(): Flow<List<Article>> = flow  {
        emit(FakeDataSource.articleList.articles)
    }

    override suspend fun upsert(article: Article) {

    }

    override suspend fun delete(article: Article) {

    }
}