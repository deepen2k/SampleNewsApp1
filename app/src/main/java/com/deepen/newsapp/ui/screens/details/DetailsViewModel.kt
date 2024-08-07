package com.deepen.newsapp.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.deepen.newsapp.NewsApplication
import com.deepen.newsapp.data.repo.NewsDatabaseRepository
import com.deepen.newsapp.data.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsViewModel(
    private val databaseRepository: NewsDatabaseRepository
) : ViewModel() {


    fun addArticle(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                databaseRepository.upsert(article)
            }

        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val dataRepository = application.container.databaseRepository
                DetailsViewModel(databaseRepository = dataRepository)
            }
        }
    }
}