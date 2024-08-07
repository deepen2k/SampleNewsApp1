package com.deepen.newsapp.fake.details

import com.deepen.newsapp.fake.home.FakeDataSource
import com.deepen.newsapp.fake.room.FakeBookmarkNewsDatabaseRepository
import com.deepen.newsapp.ui.screens.details.DetailsViewModel
import com.example.marsphotos.rules.TestDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()


    @Test
    fun detailViewModel_getArticles_verify_AddArticle() = runTest {

      var detailsViewModel = DetailsViewModel(databaseRepository = FakeBookmarkNewsDatabaseRepository())
        detailsViewModel.addArticle(FakeDataSource.articleList.articles[0])
    }
}