package com.kakao.recipes.repositories

import android.content.Context
import android.util.Log
import com.kakao.recipes.ApiClient
import com.kakao.recipes.apiInterfaces.RecipesInterface
import com.kakao.recipes.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.model.Recipe
import com.kakao.recipes.model.RecipeCategory
import com.kakao.recipes.model.RecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    context: Context
) : RecipeRepositoryInterface {

    private var recipesInterface: RecipesInterface
    val recipesList: ArrayList<Recipe> = arrayListOf()

    init {
        this.recipesInterface = ApiClient.getInstance()!!.getClient().create(RecipesInterface::class.java)

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

   /* override fun requestRecipes() {
        val call = recipesInterface.getRecipe()

        call.enqueue(object : Callback<RecipesResponse> {
            override fun onResponse(call: Call<RecipesResponse>, response: Response<RecipesResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val recipes = response.body()!!.recipes
                    recipesList.addAll(recipes)
                    Log.d("gvhvdkjdbkad", recipesList.toString())
                    Log.d("Recipes_RESPONSE", recipes.toString())
                } else {
                    Log.e("Recipes_ERROR", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
                Log.e("Recipes_ERROR", "Network call failed: ${t.localizedMessage}")
            }
        })
    }*/

    override suspend fun requestRecipes() {
        try {
            val response = recipesInterface.getRecipe()
            recipesList.clear()
            recipesList.addAll(response.recipes)
            Log.d("Recipes_SUCCESS", recipesList.toString())
        } catch (e: Exception) {
            Log.e("Recipes_ERROR", "Network error: ${e.localizedMessage}")
        }
    }


    override fun getRecipes(): List<Recipe> {

        Log.d("gvhv", recipesList.toString())
        return recipesList
    }
}


