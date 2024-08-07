package com.deepen.newsapp.fake.room

import com.deepen.newsapp.data.repo.BookmarkNewsDatabaseRepository
import com.deepen.newsapp.fake.home.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class BookmarkNewsDatabaseRepositoryTest {
    @Test
    fun bookmarkNewsDatabaseRepository_getArticles_verifyPhotoList() =
        runTest {
            val repository = BookmarkNewsDatabaseRepository(
                newsDao = FakeNewsDao()
            )

            repository.getArticles().collect{
                Assert.assertEquals(FakeDataSource.articleList.articles, it)
            }

        }

}