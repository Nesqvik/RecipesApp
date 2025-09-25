package com.kakao.recipes.interfaces

import android.content.Context
import com.kakao.recipes.data.Recipe
import com.kakao.recipes.data.RecipeCategory

interface RecipeRepositoryInterface {

    fun getRecipeCategories(): List<RecipeCategory>
    suspend fun requestRecipes(): Result<Unit>
    fun getRecipes(): List<Recipe>
    suspend fun insertRecipes()
    fun isConnected(context: Context): Boolean
    suspend fun loadMoreRecipes(): List<Recipe>?
}