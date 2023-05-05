package com.example.homework4.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homework4.data.UIArticle

@Composable
fun ArticleView(
    article: UIArticle,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick() },
        backgroundColor = Color(240, 240, 240),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp).aspectRatio(1f),
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Source: ${article.sourceName}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(article.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Autheor: ${article.author}")
            }
        }
    }
}
