package com.deepen.newsapp.fake.home

import com.deepen.newsapp.ui.screens.home.HomeNewsUiState
import com.deepen.newsapp.ui.screens.home.HomeViewModel
import com.example.marsphotos.rules.TestDispatcherRule
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule

class HomeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun homeViewModel_getLatestNews_verify_HomeNewsUiStateSuccess() =   runTest {

        var homeViewModel = HomeViewModel(homeNewsRepository = FakeNetworkHomeNewsRepository())

        homeViewModel.getLatestNews()
        assertEquals(HomeNewsUiState.Success(FakeDataSource.articleList), homeViewModel.homeNewsUiState)
    }

}