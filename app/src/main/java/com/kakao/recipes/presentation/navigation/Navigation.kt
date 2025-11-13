package com.kakao.recipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kakao.recipes.presentation.ui.screens.RecipeDetailsScreen
import com.kakao.recipes.presentation.ui.screens.RecipeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "RecipeScreen") {

        composable("RecipeScreen") {
            RecipeScreen(navController = navController)
        }
        composable(
            "recipeDetail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0 // Default to 0 if null
            RecipeDetailsScreen(navController = navController, recipeId = recipeId)
        }
    }
}

