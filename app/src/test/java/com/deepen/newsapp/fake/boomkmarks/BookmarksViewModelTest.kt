package com.deepen.newsapp.fake.boomkmarks

import com.deepen.newsapp.fake.home.FakeDataSource
import com.deepen.newsapp.fake.room.FakeBookmarkNewsDatabaseRepository
import com.deepen.newsapp.ui.screens.bookmarks.BookmarksNewsUiState
import com.deepen.newsapp.ui.screens.bookmarks.BookmarksViewModel
import com.deepen.newsapp.ui.screens.home.HomeNewsUiState
import com.example.marsphotos.rules.TestDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class BookmarksViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()


    @Test
    fun bookmarksViewModel_getArticles_verify_BookmarksNewsUiState() = runTest {

        var bookmarksViewModel = BookmarksViewModel(databaseRepository = FakeBookmarkNewsDatabaseRepository())
        bookmarksViewModel.fetchArticles()
        advanceUntilIdle()
        withContext(Dispatchers.Default) {

            delay(2000)

        }

        Assert.assertEquals(
            BookmarksNewsUiState.Success(FakeDataSource.articleList.articles),
            bookmarksViewModel.bookmarkNewsUiState
        )
    }


}