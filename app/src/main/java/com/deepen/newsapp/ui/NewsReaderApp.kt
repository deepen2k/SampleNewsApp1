package com.deepen.newsapp.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepen.newsapp.R
import com.deepen.newsapp.data.models.Article
import com.deepen.newsapp.ui.screens.categories.CategoriesScreen
import com.deepen.newsapp.ui.screens.details.DetailsScreen
import com.deepen.newsapp.ui.screens.home.HomeScreen
import com.deepen.newsapp.ui.screens.home.HomeViewModel
import com.deepen.newsapp.ui.screens.bookmarks.BookmarksScreen
import com.deepen.newsapp.ui.screens.bookmarks.BookmarksViewModel

import com.deepen.newsapp.ui.screens.categories.CategoriesViewModel
import com.deepen.newsapp.ui.screens.categories.CategoryUiState
import com.deepen.newsapp.ui.screens.home.HomeNewsUiState


sealed class HomeNavDestination(
    val title: String,
    val route: String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector,
    val index:Int
) {

    object Home : HomeNavDestination(
        title = "Home",
        route = "home_screen",
        icon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        index = 0
    )

    object Categories : HomeNavDestination(
        title = "Categories",
        route = "categories_screen",
        icon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
        index = 1
    )

    object Bookmark : HomeNavDestination(
        title = "Bookmark",
        route = "bookmark_screen",
        icon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.Favorite,
        index = 2
    )


}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsReaderApp(navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val items = listOf(
        HomeNavDestination.Home, HomeNavDestination.Categories, HomeNavDestination.Bookmark
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }


    BackHandler {
        // Do stuff..

       var selectedi = selectedItemIndex


    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            HomeBody(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
            ,{
                selectedItemIndex = it
                })
        },
        bottomBar = {
            NavigationBar(

            ) {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = null)
                        },
                        label = { Text(screen.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    )
                }
            }
        }


    )

}

@Composable
fun HomeBody(navController: NavHostController, modifier: Modifier,onBackHandler:((index:Int)->Unit)?) {
    NavHost(
        navController = navController, startDestination = HomeNavDestination.Home.route
    ) {

        composable(HomeNavDestination.Home.route) {
            val homeNewsViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                homeNewsUiState = homeNewsViewModel.homeNewsUiState,
                navController,
                modifier = modifier
            )
        }
        composable("Detail") {

            navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.let {
                DetailsScreen(article = it, modifier = modifier, onBackClicked = {
                    navController.popBackStack()
                })
            }
        }

        composable(HomeNavDestination.Categories.route) {
            CategoriesScreen(modifier = modifier, navController,{
                if (onBackHandler != null) {
                    onBackHandler(0)
                }
            })
        }
        composable(HomeNavDestination.Bookmark.route) {

            BookmarksScreen(modifier = modifier,navController,{
                if (onBackHandler != null) {
                    onBackHandler(0)
                }
            })
        }

    }

}
/*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,

        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
*/


