package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    operator fun invoke(query: String): Flow<List<Recipe>> {
        return repository.searchRecipesByName("%$query%")
    }
}
