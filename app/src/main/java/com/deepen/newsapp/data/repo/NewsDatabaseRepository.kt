package com.deepen.newsapp.data.repo

import com.deepen.newsapp.data.models.Article
import com.loc.newsapp.data.local.NewsDao
import kotlinx.coroutines.flow.Flow


interface NewsDatabaseRepository {
    fun getArticles(): Flow<List<Article>>
    suspend fun upsert(article: Article)
    suspend fun delete(article: Article)
}


class BookmarkNewsDatabaseRepository(
    private val newsDao: NewsDao
) : NewsDatabaseRepository {
    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

    override suspend fun upsert(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun delete(article: Article) {
        newsDao.delete(article)
    }
}