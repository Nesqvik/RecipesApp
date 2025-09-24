package com.kakao.recipes.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.recipes.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.data.Recipe
import com.kakao.recipes.data.RecipeCategory
import com.kakao.recipes.local.RecipeDao
import com.kakao.recipes.useCases.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(

    private val recipeRepositoryInterface: RecipeRepositoryInterface,
    private val recipeDao: RecipeDao,
    private val getRecipesUseCase: GetRecipesUseCase


) : ViewModel() {

    private val _recipeCategories = MutableStateFlow<List<RecipeCategory>>(emptyList())
    val recipeCategories: StateFlow<List<RecipeCategory>> = _recipeCategories

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe


    fun getRecipeById(id: Int) {
        viewModelScope.launch {
            recipeDao.getRecipeById(id).collect { recipe ->
                _recipe.value = recipe
            }
        }
    }

    fun getRecipeCategories() {
        _recipeCategories.value = recipeRepositoryInterface.getRecipeCategories()
    }

    fun getRecipes() {
        viewModelScope.launch {
            try {
                recipeRepositoryInterface.requestRecipes()
                //_recipes.value = recipeRepositoryInterface.getRecipes()

                recipeDao.getRecipes().collect { recipes ->
                    _recipes.value = recipes
                    Log.d("DatabaseCheckddd", "Recipes fetched: $recipes")
                }

                Log.d("DatabaseCheck", recipeDao.getRecipes().first().toString())
                Log.d("DatabaseCheck", recipeDao.getRecipeById(631747).toString())


            } catch (e: Exception) {
                Log.e("RecipesViewModel", "Failed to fetch recipes: ${e.message}")
            }
        }
    }
}