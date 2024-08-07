package com.deepen.newsapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performRotaryScrollInput
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.deepen.newsapp.ui.NewsReaderApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setupHost() {
        composeTestRule.setContent {

            NewsReaderApp()
        }
    }

    @Test
    fun checkAppBarVisible() = runTest {

        //Wait for it to load
        withContext(Dispatchers.Main){
            delay(200)
        }
      composeTestRule.onNodeWithText("News App",substring = true, ignoreCase = true).assertExists()

    }

    @Test
    fun checknewsList() = runTest {
        //Wait for it to load
        withContext(Dispatchers.Main){
            delay(200)
        }
        composeTestRule.onNodeWithText("Bonus: HARDtalk - The Whistleblowers",substring = true, ignoreCase = true).assertExists()
    }

    @Test
    fun checknewsDetails() = runTest {
        //Wait for it to load
        withContext(Dispatchers.Main){
            delay(200)
        }
        composeTestRule.onNodeWithText("Bonus: HARDtalk - The Whistleblowers",substring = true, ignoreCase = true).performClick()
        withContext(Dispatchers.Main){
            delay(200)
        }
        composeTestRule.onNodeWithText("In a special edition of",substring = true, ignoreCase = true).assertExists()
    }



}