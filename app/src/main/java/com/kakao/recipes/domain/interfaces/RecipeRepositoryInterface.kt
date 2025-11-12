package com.kakao.recipes.domain.interfaces

import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.model.RecipeCategory
import kotlinx.coroutines.flow.Flow

interface RecipeRepositoryInterface {

    fun getRecipeCategories(): List<RecipeCategory>
    suspend fun requestRecipes(): Result<Unit>
    suspend fun loadMoreRecipes(): List<Recipe>?
    fun getRecipeById(id: Int): Flow<Recipe>
    fun getRecipes(): Flow<List<Recipe>>
    fun searchRecipesByName(query: String): Flow<List<Recipe>>
}