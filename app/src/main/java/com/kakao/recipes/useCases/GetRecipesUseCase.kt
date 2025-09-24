package com.kakao.recipes.useCases

import com.kakao.recipes.data.Recipe
import com.kakao.recipes.interfaces.RecipeRepositoryInterface
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeRepositoryInterface: RecipeRepositoryInterface
) {

    fun getRecipes(): List<Recipe> {
        return recipeRepositoryInterface.getRecipes()
    }

}
