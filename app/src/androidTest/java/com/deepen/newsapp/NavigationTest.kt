package com.deepen.newsapp


import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import org.junit.Rule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.deepen.newsapp.ui.HomeNavDestination
import com.deepen.newsapp.ui.NewsReaderApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            NewsReaderApp(navController = navController)
        }
    }

    @Test
    fun newsNavHost_verifyStartDestination() {
     navController.assertCurrentRouteName(HomeNavDestination.Home.route)
        //navController.ass
    }

    @Test
    fun newsNavHost_click_navigatesToCategoryScreen() {
        composeTestRule.onNodeWithText(HomeNavDestination.Categories.title).performClick()
        navController.assertCurrentRouteName(HomeNavDestination.Categories.route)
    }



    // Note works with static data is loaded in homoe view model.
    @Test
    fun newsNavHost_click_navigatesToNewsDetail()  = runTest{
        withContext(Dispatchers.Default) { delay(2000) }
        composeTestRule.onNodeWithText("Bonus: HARDtalk - The Whistleblowers", substring = true,ignoreCase = true, useUnmergedTree = true).performClick()
        navController.assertCurrentRouteName("Detail")
    }



    @Test
    fun newsNavHost_click_navigatesToBookMarkScreen() {
        composeTestRule.onNodeWithText(HomeNavDestination.Bookmark.title).performClick()
        navController.assertCurrentRouteName(HomeNavDestination.Bookmark.route)
    }

}

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}


fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))

