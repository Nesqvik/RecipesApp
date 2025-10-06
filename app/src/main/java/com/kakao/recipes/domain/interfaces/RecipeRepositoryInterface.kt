package com.kakao.recipes.domain.interfaces

import android.content.Context
import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.model.RecipeCategory

interface RecipeRepositoryInterface {

    fun getRecipeCategories(): List<RecipeCategory>
    suspend fun requestRecipes(): Result<Unit>
    fun getRecipes(): List<Recipe>
    suspend fun insertRecipes()
    fun isConnected(context: Context): Boolean
    suspend fun loadMoreRecipes(): List<Recipe>?
}