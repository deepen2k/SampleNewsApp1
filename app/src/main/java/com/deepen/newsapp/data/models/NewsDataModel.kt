package com.deepen.newsapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class NewsDataModel(
    val status: String,
    val totalResults: Long,
    val articles: List<Article>,
)

@Parcelize
@Entity
data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    @PrimaryKey val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
) : Parcelable

@Parcelize
data class Source(
    val id: String,
    val name: String,
) : Parcelable