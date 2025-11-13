package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetRecipeByIdUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    operator fun invoke(id: Int): Flow<Recipe> {
        return repository.getRecipeById(id)
    }
}
