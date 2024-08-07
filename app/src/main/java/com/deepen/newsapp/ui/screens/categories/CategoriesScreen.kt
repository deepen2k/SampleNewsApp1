package com.deepen.newsapp.ui.screens.categories

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.deepen.newsapp.data.models.NewsCategory
import com.deepen.newsapp.ui.common.CustomNewsTopBar
import com.deepen.newsapp.ui.common.ResultScreen

import com.deepen.newsapp.ui.screens.home.NewsList


@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController?,
    onBackHandler: (() -> Unit)?
) {

    val catNewsViewModel: CategoriesViewModel = viewModel(factory = CategoriesViewModel.Factory)
    var selCategory by remember { mutableStateOf("Categories") }

    BackHandler {
        if (catNewsViewModel.catNewsUiState != CategoryUiState.Category_List) {
            catNewsViewModel.showCategoriesList()
        } else {
            navHostController?.popBackStack()
            if (onBackHandler != null) {
                onBackHandler()
            }
        }
    }

    when (catNewsViewModel.catNewsUiState) {
        is CategoryUiState.Error -> ResultScreen(text = " ...Error Fetching data")
        is CategoryUiState.Loading -> ResultScreen(text = "...Loading")
        is CategoryUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            )
            {
                CategoryListTopBar(selCategory, onBackClicked = {
                    catNewsViewModel.showCategoriesList()
                })
                NewsList((catNewsViewModel.catNewsUiState as CategoryUiState.Success).news.articles.filter {
                    !it.title.contains("Removed")
                }, modifier = modifier, navController = navHostController)
            }

        }

        is CategoryUiState.Category_List -> {
            CustomNewsTopBar("Categories")
            ShowCategories(NewsCategory().catList, {

                //catNewsViewModel.getStaticData()
                catNewsViewModel.getLatestNews(it)
                selCategory = it

            }, modifier = modifier)
        }
    }


}

@Composable
fun ShowCategories(
    catitems: List<String>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),

        contentPadding = PaddingValues(top = 40.dp)
    ) {
        items(catitems) {

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),

                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clickable {
                        onCategoryClick(it)
                    }
            ) {
                Text(
                    text = it.capitalize(),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListTopBar(category: String = "Top Headlines", onBackClicked: (() -> Unit)?) {
    TopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        title = {
            Text(
                text = category.capitalize(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (onBackClicked != null) {
                    onBackClicked()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

@Preview
@Composable
fun CatPReview() {
    CategoriesScreen(navHostController = null, onBackHandler = null)
}

