package com.deepen.newsapp.ui.screens.categories

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.deepen.newsapp.NewsApplication
import com.deepen.newsapp.data.models.NewsDataModel
import com.deepen.newsapp.data.repo.HomeNewsRepository
import com.deepen.newsapp.data.staticsource.StaticData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface CategoryUiState {
    data class Success(val news: NewsDataModel) : CategoryUiState
    object Error : CategoryUiState
    object Loading : CategoryUiState
    object Category_List : CategoryUiState
}


class CategoriesViewModel(
    private val homeNewsRepository: HomeNewsRepository
) : ViewModel() {


    var catNewsUiState: CategoryUiState by mutableStateOf(CategoryUiState.Category_List)
        private set


    fun getStaticData() {
        viewModelScope.launch {
            catNewsUiState = CategoryUiState.Loading
            delay(2000)

            var gson = Gson()
            var testModel = gson.fromJson(StaticData.STATIC_DATA3, NewsDataModel::class.java)

            catNewsUiState = CategoryUiState.Success(
                testModel
            )
        }
    }

    fun showCategoriesList() {
        catNewsUiState = CategoryUiState.Category_List
    }

    fun getLatestNews(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            catNewsUiState = CategoryUiState.Loading

            catNewsUiState = try {
                val listResult = homeNewsRepository.getCategoryNews(category = category)
                CategoryUiState.Success(
                    listResult
                )
            } catch (e: Exception) {
                CategoryUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)
                val homeNewsRepository = application.container.homeNewsRepository
                CategoriesViewModel(homeNewsRepository = homeNewsRepository)
            }
        }
    }
}