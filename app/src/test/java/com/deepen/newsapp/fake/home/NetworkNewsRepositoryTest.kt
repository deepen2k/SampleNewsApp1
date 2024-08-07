package com.deepen.newsapp.fake.home

import com.deepen.newsapp.data.repo.NetworkHomeNewsRepository
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals


class NetworkNewsRepositoryTest  {

    @Test
    fun networkMarsPhotosRepository_getArticles() =
        runTest {
            val repository = NetworkHomeNewsRepository(
                newsApiService = FakeNewsApiService()
            )
            assertEquals(FakeDataSource.articleList, repository.getLatestNews())
        }

}