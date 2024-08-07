package com.deepen.newsapp.ui.screens.bookmarks

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.deepen.newsapp.R
import com.deepen.newsapp.data.models.Article
import com.deepen.newsapp.data.models.NewsDataModel
import com.deepen.newsapp.ui.common.AlertDialogExample
import com.deepen.newsapp.ui.common.CustomNewsTopBar
import com.deepen.newsapp.ui.common.NewsCard
import com.deepen.newsapp.ui.screens.categories.CategoryUiState
import com.deepen.newsapp.ui.screens.home.HomeNewsUiState
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController?,
    onBackHandler: (() -> Unit)?
) {

    val shouldShowDialog = remember { mutableStateOf(false) }
    val selectedArticle = remember { mutableStateOf<Article?>(null) }
    // ShowOtherState(isEmpty = true)
    val bookmarksViewModel: BookmarksViewModel = viewModel(factory = BookmarksViewModel.Factory)

    BackHandler {
        navHostController?.popBackStack()
        if (onBackHandler != null) {
            onBackHandler()
        }
    }

    when (bookmarksViewModel.bookmarkNewsUiState) {
        is BookmarksNewsUiState.Empty -> ShowOtherState(isEmpty = true)
        is BookmarksNewsUiState.Loading -> ShowOtherState(isEmpty = false)
        is BookmarksNewsUiState.Success -> {
            Column {
                CustomNewsTopBar(title = "Bookmarks")
                BookmarksList(
                    newsArticles = (bookmarksViewModel.bookmarkNewsUiState as BookmarksNewsUiState.Success).articles,
                    modifier,
                    {
                        shouldShowDialog.value = true
                        selectedArticle.value = it
                    })
            }

            if (shouldShowDialog.value) {
                AlertDialogExample(
                    { shouldShowDialog.value = false },
                    {
                        shouldShowDialog.value = false
                        selectedArticle.value?.let { article ->
                            bookmarksViewModel.removeBookmark(
                                artile = article
                            )
                        }

                    },
                    stringResource(R.string.confrim_delete),
                    stringResource(R.string.are_you_sure_you_want_to_remove_this_bookmark)
                )
            }
        }
    }

}

@Composable
fun ShowOtherState(isEmpty: Boolean, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = when (isEmpty) {
                true -> stringResource(R.string.no_bookmarks_found)
                false -> stringResource(R.string.loading_bookmarks)
            },
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp
        )
    }
}


@Composable
fun BookmarksList(
    newsArticles: List<Article>,
    modifier: Modifier = Modifier,
    onClickedItem: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(all = 15.dp)
    ) {
        items(newsArticles) {
            //NewsItem(it)
            NewsCard(article = it, onClick = {
                onClickedItem(it)
            })
        }

    }

}


@Preview
@Composable
fun previewBookmarks() {
    BookmarksScreen(navHostController = null, onBackHandler = null)
}