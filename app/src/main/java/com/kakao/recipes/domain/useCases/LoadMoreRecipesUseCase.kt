package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.model.Recipe
import javax.inject.Inject


class LoadMoreRecipesUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    suspend operator fun invoke(): List<Recipe>? {
        return repository.loadMoreRecipes()
    }
}