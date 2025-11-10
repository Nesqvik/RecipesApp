package com.kakao.recipes.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.kakao.recipes.ApiClient
import com.kakao.recipes.RecipesKeys
import com.kakao.recipes.data.apiInterfaces.RecipesInterface
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.model.RecipeCategory
import com.kakao.recipes.data.local.RecipeDao
import java.io.IOException
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    private val context: Context,
    val recipeDao: RecipeDao
) : RecipeRepositoryInterface {

    private var recipesInterface: RecipesInterface
    val recipesList: ArrayList<Recipe> = arrayListOf()


    init {
        this.recipesInterface =
            ApiClient.getInstance()!!.getClient().create(RecipesInterface::class.java)
    }

    override fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override suspend fun loadMoreRecipes(): List<Recipe>? {
        return try {
            val response = recipesInterface.getRecipe()
            val newRecipes = response.recipes
            recipeDao.insertAll(newRecipes)
            newRecipes
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getRecipeCategories(): List<RecipeCategory> {
        return listOf(
            RecipeCategory(RecipesKeys.MAIN_COURSE, "Show all"),
            RecipeCategory(RecipesKeys.SIDE_DISH, "Side Dish"),
            RecipeCategory(RecipesKeys.DESSERT, "Dessert"),
            RecipeCategory(RecipesKeys.APPETIZER, "Appetizer"),
            RecipeCategory(RecipesKeys.SALAD, "Salad")
        )
    }

    override suspend fun requestRecipes() : Result<Unit> {

        if (!isConnected(context)) {
            return Result.failure(IOException("No internet"))
        }
        return try {
            val response = recipesInterface.getRecipe()
            recipesList.clear()
            recipesList.addAll(response.recipes)
            insertRecipes()
            Result.success(Unit)

        } catch (e: Exception) {
            Log.e("Recipes_ERROR", "Network error: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    override fun getRecipes(): List<Recipe> {
        return recipesList
    }

    override suspend fun insertRecipes() {
        recipeDao.insertAll(recipesList)
    }

}




