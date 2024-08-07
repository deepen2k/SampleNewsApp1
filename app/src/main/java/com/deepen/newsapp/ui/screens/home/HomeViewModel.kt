package com.deepen.newsapp.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.deepen.newsapp.NewsApplication
import com.deepen.newsapp.data.staticsource.StaticData.STATIC_DATA3
import com.deepen.newsapp.data.repo.HomeNewsRepository
import com.deepen.newsapp.data.models.NewsDataModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeNewsUiState {
    data class Success(val news: NewsDataModel) : HomeNewsUiState
    object Error : HomeNewsUiState
    object Loading : HomeNewsUiState
}

class HomeViewModel(
    private val homeNewsRepository: HomeNewsRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    var homeNewsUiState: HomeNewsUiState by mutableStateOf(HomeNewsUiState.Loading)
        private set

    init {
        getLatestNews()
    }

    //Used only to speed up dev process , remove it from code during prod or use macros.
    fun getStaticData() {

        viewModelScope.launch {
            homeNewsUiState = HomeNewsUiState.Loading

            delay(200)

            var gson = Gson()
            var testModel = gson.fromJson(STATIC_DATA3, NewsDataModel::class.java)

            homeNewsUiState = HomeNewsUiState.Success(
                testModel
            )
        }
    }


    fun getPeriodicLatestNews() {
        viewModelScope.launch {
            homeNewsUiState = HomeNewsUiState.Loading

            val listResult = homeNewsRepository.getPeriodicNews().flowOn(Dispatchers.IO)
                .catch { e ->

                    homeNewsUiState = HomeNewsUiState.Error
                }
                .collect {
                    homeNewsUiState = HomeNewsUiState.Success(it)

                }

        }

    }


    fun getLatestNews() {
        viewModelScope.launch {
            homeNewsUiState = HomeNewsUiState.Loading

            //delay(2000)
            homeNewsUiState = try {
                val listResult = homeNewsRepository.getLatestNews()
                HomeNewsUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                HomeNewsUiState.Error
            } catch (e: HttpException) {
                HomeNewsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NewsApplication)
                val homeNewsRepository = application.container.homeNewsRepository
                HomeViewModel(homeNewsRepository = homeNewsRepository)
            }
        }
    }
}