package com.kakao.recipes.interfaces

import com.kakao.recipes.model.Recipe
import com.kakao.recipes.model.RecipeCategory

interface RecipeRepositoryInterface {

    fun getRecipeCategories(): List<RecipeCategory>
    suspend fun requestRecipes()
    fun getRecipes(): List<Recipe>
}