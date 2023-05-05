package com.example.homework4.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homework4.data.UIArticle
import com.example.homework4.presentation.DataLoaderViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    articles: State<List<UIArticle>>,
    viewModel: DataLoaderViewModel
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val categories = listOf(
        "general",
        "technology",
        "business",
        "science",
        "sports",
        "health"
    )

    var search by remember { mutableStateOf("") }
    var dropDownExpanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Column {
        Text(
            "News",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(37, 49, 91))
                .padding(8.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(37, 49, 91))
                .padding(8.dp),
        ) {
            TextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.weight(1f),
                maxLines = 1,
                leadingIcon = {
                    IconButton(onClick = {
                        dropDownExpanded = true
                    }
                    ) {
                        Icon(
                            imageVector = if (dropDownExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "categories",
                        )
                    }
                },
                placeholder = { Text(text = "Search") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            IconButton(onClick = {
                viewModel.searchNews(search, selectedCategory)
                keyboardController?.hide()
                focusManager.clearFocus()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                )
            }

            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .height(20.dp),
                        onClick = {
                            dropDownExpanded = false
                            selectedCategory = category
                            viewModel.searchNews(search, category)
                        }
                    ) {
                        val isSelected = category == selectedCategory
                        Text(
                            text = category,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color(37, 49, 91) else Color.Black,
                            textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }

        }

        LazyColumn {
            items(articles.value) { article ->
                ArticleView(
                    article = article,
                    onItemClick = {
                        navController.navigate(
                            "details/${articles.value.indexOf(article)}"
                        )
                    }
                )
            }
        }
    }
}