package com.kakao.recipes.presentation.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.model.RecipeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.setValue
import com.kakao.recipes.domain.useCases.RecipeUseCases
import com.kakao.recipes.core.util.Result


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases

) : ViewModel() {

    private val _recipeCategories = MutableStateFlow<List<RecipeCategory>>(emptyList())
    val recipeCategories: StateFlow<List<RecipeCategory>> = _recipeCategories

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    private val _noInternet = MutableStateFlow<Boolean>(false)
    val noInternet: StateFlow<Boolean> = _noInternet

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _allRecipes = MutableStateFlow<List<Recipe>>(emptyList())

    private var isFiltering by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isEndReached by mutableStateOf(false)

    init {
        viewModelScope.launch {
            searchQuery.collect { query ->
                searchRecipes(query)
            }
        }
    }

    fun loadNextPage() {
        if (isLoading || isEndReached || isFiltering) return

        viewModelScope.launch {
            isLoading = true
            val more = recipeUseCases.loadMoreRecipes()
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

    private fun searchRecipes(query: String) {
        viewModelScope.launch {
            recipeUseCases.searchRecipes("%$query%").collect { recipesList ->
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
            recipeUseCases.getRecipeById(id).collect { recipe ->
                _recipe.value = recipe
            }
        }
    }

    fun getRecipeCategories() {
        _recipeCategories.value = recipeUseCases.getRecipeCategories()
    }

    fun getRecipes() {
        viewModelScope.launch {
            recipeUseCases.getRecipes()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            isLoading = true
                        }

                        is Result.Success -> {
                            isLoading = false
                            _noInternet.value = false
                            _recipes.value = result.data
                            _allRecipes.value = result.data
                        }

                        is Result.Error -> {
                            isLoading = false
                            _noInternet.value = true
                            Log.e("RecipesViewModel", "Error: ${result.exception?.message}")
                        }
                    }
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