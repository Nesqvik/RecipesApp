package com.kakao.recipes.interfaces

import com.kakao.recipes.data.Recipe
import com.kakao.recipes.data.RecipeCategory

interface RecipeRepositoryInterface {

    fun getRecipeCategories(): List<RecipeCategory>
    suspend fun requestRecipes()
    fun getRecipes(): List<Recipe>
    suspend fun insertRecipes()
}