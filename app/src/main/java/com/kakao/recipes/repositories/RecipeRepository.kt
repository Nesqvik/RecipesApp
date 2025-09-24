package com.kakao.recipes.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kakao.recipes.ApiClient
import com.kakao.recipes.apiInterfaces.RecipesInterface
import com.kakao.recipes.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.data.Recipe
import com.kakao.recipes.data.RecipeCategory
import com.kakao.recipes.local.RecipeDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    context: Context,
    val recipeDao: RecipeDao
) : RecipeRepositoryInterface {

    private var recipesInterface: RecipesInterface
    val recipesList: ArrayList<Recipe> = arrayListOf()

    init {
        this.recipesInterface =
            ApiClient.getInstance()!!.getClient().create(RecipesInterface::class.java)

    }

    override fun getRecipeCategories(): List<RecipeCategory> {
        return listOf(
            RecipeCategory(1, "Main Course"),
            RecipeCategory(2, "Side Dish"),
            RecipeCategory(3, "Dessert"),
            RecipeCategory(4, "Appetizer"),
            RecipeCategory(5, "Salad")
        )
    }

    override suspend fun requestRecipes() {
        try {
            val response = recipesInterface.getRecipe()

               // recipesList.clear()
                recipesList.addAll(response.recipes)
                insertRecipes()


            Log.d("Recipes_SUCCESS", recipesList.toString())
        } catch (e: Exception) {
            Log.e("Recipes_ERROR", "Network error: ${e.localizedMessage}")
        }
    }

    override fun getRecipes(): List<Recipe> {
        return recipesList
    }

    override suspend fun insertRecipes() {
        recipeDao.insertAll(recipesList)
    }

}




