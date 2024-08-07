package com.deepen.newsapp.data

import android.content.Context
import com.deepen.newsapp.data.repo.BookmarkNewsDatabaseRepository
import com.deepen.newsapp.data.repo.NewsDatabaseRepository
import com.deepen.newsapp.data.repo.HomeNewsRepository
import com.deepen.newsapp.data.repo.NetworkHomeNewsRepository
import com.deepen.newsapp.network.NewsAPIService
import com.deepen.newsapp.utils.Constants.baseUrl
import com.loc.newsapp.data.local.NewsDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val homeNewsRepository: HomeNewsRepository
    val databaseRepository: NewsDatabaseRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: NewsAPIService by lazy {
        retrofit.create(NewsAPIService::class.java)
    }
    override val homeNewsRepository: HomeNewsRepository
            by lazy { NetworkHomeNewsRepository(retrofitService) }

    override val databaseRepository: NewsDatabaseRepository
            by lazy { BookmarkNewsDatabaseRepository(NewsDatabase.getDatabase(context).newsDao()) }


}