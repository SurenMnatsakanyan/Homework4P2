package com.mobiledev.news_app.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.homework4.presentation.DataLoaderViewModel
import com.example.homework4.presentation.composables.DetailScreen
import com.example.homework4.presentation.composables.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(viewModel: DataLoaderViewModel) {
    val navController = rememberNavController()
    val articles = viewModel.articles.observeAsState(emptyList())

    NavHost(
        navController = navController,
        startDestination = "news"
    ) {
        composable("news") {
            HomeScreen(
                navController,
                articles,
                viewModel = viewModel
            )
        }

        composable(
            "details/{articleIndex}",
            arguments = listOf(navArgument("articleIndex") { type = NavType.IntType })
        ) { entry ->
            entry.arguments?.getInt("articleIndex")?.let { index ->
                DetailScreen(
                    navController = navController,
                    article = articles.value[index]
                )
            }
        }
    }
}