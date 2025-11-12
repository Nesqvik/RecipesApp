package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import javax.inject.Inject


class RequestRecipesUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val recipes = repository.requestRecipes()
           // repository.insertRecipes(recipes.)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
