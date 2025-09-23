package com.kakao.recipes.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.recipes.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.model.Recipe
import com.kakao.recipes.model.RecipeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(

    private val recipeRepositoryInterface: RecipeRepositoryInterface,

    ) : ViewModel() {

   // val categories: List<RecipeCategory> = recipeRepositoryInterface.getRecipeCategories()


    private val _recipeCategories = MutableStateFlow<List<RecipeCategory>>(emptyList())
    val recipeCategories: StateFlow<List<RecipeCategory>> = _recipeCategories

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes


    fun getRecipeCategories() {
        _recipeCategories.value = recipeRepositoryInterface.getRecipeCategories()
    }

    fun getRecipes2() {
        _recipes.value = recipeRepositoryInterface.getRecipes()
    }

    fun getRecipes() {
        viewModelScope.launch {
            try {
                recipeRepositoryInterface.requestRecipes()
                _recipes.value = recipeRepositoryInterface.getRecipes()
            } catch (e: Exception) {
                Log.e("RecipesViewModel", "Failed to fetch recipes: ${e.message}")
            }
        }
    }

}