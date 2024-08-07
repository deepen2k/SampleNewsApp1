package com.deepen.newsapp.ui.screens.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.deepen.newsapp.NewsApplication
import com.deepen.newsapp.data.repo.NewsDatabaseRepository
import com.deepen.newsapp.data.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

sealed interface BookmarksNewsUiState {
    data class Success(val articles: List<Article>) : BookmarksNewsUiState
    object Empty : BookmarksNewsUiState
    object Loading : BookmarksNewsUiState
}

class BookmarksViewModel(
    private val databaseRepository: NewsDatabaseRepository
) : ViewModel() {

    var bookmarkNewsUiState: BookmarksNewsUiState by mutableStateOf(BookmarksNewsUiState.Loading)
        private set


    init {
        fetchArticles()
    }

    fun removeBookmark(artile: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.delete(article = artile)
        }
    }

    fun fetchArticles() {
        viewModelScope.launch {
            bookmarkNewsUiState = BookmarksNewsUiState.Loading
            databaseRepository.getArticles()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // handle exception
                    bookmarkNewsUiState = BookmarksNewsUiState.Empty
                }
                .collect {
                    if (it.size > 0) {
                        bookmarkNewsUiState = BookmarksNewsUiState.Success(
                            it
                        )
                    } else
                        bookmarkNewsUiState = BookmarksNewsUiState.Empty

                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val dataRepository = application.container.databaseRepository
                BookmarksViewModel(databaseRepository = dataRepository)
            }
        }
    }
}