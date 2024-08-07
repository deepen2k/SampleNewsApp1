package com.deepen.newsapp.data.models

data class NewsCategory(
    val catList: List<String> = listOf(
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology"
    )
)

