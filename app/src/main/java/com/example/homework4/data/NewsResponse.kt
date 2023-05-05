package com.example.homework4.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class NewsResponse(
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?,
    @SerializedName("articles")
    var articles: List<ArticleResponse>?,
) {
    data class ArticleResponse(
        @SerializedName("source")
        var source: SourceResponse?,
        @SerializedName("author")
        var author: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("url")
        var url: String?,
        @SerializedName("urlToImage")
        var urlToImage: String?,
        @SerializedName("publishedAt")
        var publishedAt: String?,
        @SerializedName("content")
        var content: String?,
    ) {
        data class SourceResponse(
            @SerializedName("id")
            var id: String?,
            @SerializedName("name")
            var name: String?,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NewsResponse.ArticleResponse.toUIArticle() = UIArticle(
    sourceName = source?.name ?: "N/A",
    author = author ?: "N/A",
    title = title ?: "N/A",
    imageUrl = urlToImage,
    url = url,
    publishedAt = DateTimeFormatter
        .ofPattern("dd-MM-yy HH:mm")
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())
        .format(Instant.parse(publishedAt)) ?: "",
    content = content,
)
