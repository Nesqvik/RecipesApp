package com.kakao.recipes.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

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

    var isLoading by mutableStateOf(false)
    private set


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        // Observe the search query changes and update the recipe list
        viewModelScope.launch {
            searchQuery.collect { query ->
                loadRecipes(query)
            }
        }
    }

    private fun loadRecipes(query: String) {
        viewModelScope.launch {
            recipeDao.searchRecipesByName("%$query%").collect { recipesList ->
                _recipes.value = recipesList
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

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