package com.deepen.newsapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.deepen.newsapp.ui.NewsReaderApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoriesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupHost() {
        composeTestRule.setContent {
            NewsReaderApp()
        }
    }


    @Test
    fun checkCategories() = runTest {

        withContext(Dispatchers.IO)
        {
            composeTestRule.onNodeWithText("Categories").performClick()
            delay(500)
        }
        composeTestRule.onNodeWithText("General").assertExists()
    }

    @Test
    fun checkCategoriesListClicked() = runTest {

        withContext(Dispatchers.IO)
        {
            composeTestRule.onNodeWithText("Categories").performClick()
            delay(500)
            composeTestRule.onNodeWithText("General").performClick()
            delay(2000) //This depends on n/w so test in static data
        }

        composeTestRule.onNodeWithText("General",substring = true,ignoreCase = true, useUnmergedTree = true).assertExists()
    }


}