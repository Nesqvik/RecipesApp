package com.kakao.recipes.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.model.RecipeCategory
import com.kakao.recipes.data.local.RecipeDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull


@HiltViewModel
class RecipeViewModel @Inject constructor(

    private val recipeRepositoryInterface: RecipeRepositoryInterface,
    private val recipeDao: RecipeDao,

    ) : ViewModel() {

    private val _recipeCategories = MutableStateFlow<List<RecipeCategory>>(emptyList())
    val recipeCategories: StateFlow<List<RecipeCategory>> = _recipeCategories

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    var isLoading by mutableStateOf(false)
    var isEndReached by mutableStateOf(false)

    private val _noInternet = MutableStateFlow<Boolean>(false)
    val noInternet: StateFlow<Boolean> = _noInternet

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    private val _allRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    private var isFiltering by mutableStateOf(false)


    init {
        loadInitialRecipes()

        viewModelScope.launch {
            searchQuery.collect { query ->
                loadRecipes(query)
            }
        }
    }

    private fun loadInitialRecipes() {
        viewModelScope.launch {
            isLoading = true
            val new = recipeRepositoryInterface.loadMoreRecipes()
            if (new == null) {
                //_noInternet.value = true
            } else {
                //_noInternet.value = false
                if (new.isEmpty()) {
                    isEndReached = true
                }
            }
            delay(2000)
            isLoading = false
        }
    }

    fun loadNextPage() {
        if (isLoading || isEndReached || isFiltering) return

        viewModelScope.launch {
            isLoading = true
            val more = recipeRepositoryInterface.loadMoreRecipes()
            if (more == null) {
                //_noInternet.value = true
            } else {
                //_noInternet.value = false
                if (more.isEmpty()) {
                    isEndReached = true
                }
            }
            isLoading = false
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
        isFiltering = query.isNotBlank()
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
                val localRecipes = recipeDao.getRecipes().firstOrNull()
                if (localRecipes.isNullOrEmpty()) {

                    val result = recipeRepositoryInterface.requestRecipes()

                    if (result.isFailure) {
                        _noInternet.value = true
                        return@launch
                    } else {
                        _noInternet.value = false
                    }

                    if (result.isFailure) {
                        return@launch
                    }
                }
                recipeDao.getRecipes().collect { recipes ->
                    _recipes.value = recipes
                    _allRecipes.value = recipes
                }

            } catch (e: Exception) {
                Log.e("RecipesViewModel", "Failed to fetch recipes: ${e.message}")
            }
        }
    }

    fun filterBySelectedCategory(categoryName: String) {
        isFiltering = true
        val filteredList = _allRecipes.value.filter { recipe ->
            recipe.dishTypes.any { it.equals(categoryName, ignoreCase = true) }
        }
        _recipes.value = filteredList
    }

    fun resetFilters() {
        isFiltering = false
        _recipes.value = _allRecipes.value
    }

}