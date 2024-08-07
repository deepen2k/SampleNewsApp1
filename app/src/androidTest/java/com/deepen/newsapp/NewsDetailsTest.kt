package com.deepen.newsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
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

class NewsDetailsTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupHost() {
        composeTestRule.setContent {

            NewsReaderApp()
        }
    }

    @Test
    fun checknewsDetails() = runTest {
        //Wait for it to load
        withContext(Dispatchers.IO){
            delay(200)
            composeTestRule.onNodeWithText("Bonus: HARDtalk - The Whistleblowers",substring = true, ignoreCase = true).performClick()
            delay(2000)
        }
        composeTestRule.onNodeWithText("In a special edition of ",substring = true, ignoreCase = true).assertExists()

    }


}