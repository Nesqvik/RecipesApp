package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeRepositoryInterface: RecipeRepositoryInterface
) {

    fun getRecipes(): List<Recipe> {
        return recipeRepositoryInterface.getRecipes()
    }

}
