package com.deepen.newsapp

import android.app.Application
import com.deepen.newsapp.data.AppContainer
import com.deepen.newsapp.data.DefaultAppContainer

class NewsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}