package com.deepen.newsapp.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Constants {
    const val apiKey: String = "9ac0ab4189fa4eb3a12e5c5f4d65449f"
    const val baseUrl = "https://newsapi.org/"
}



