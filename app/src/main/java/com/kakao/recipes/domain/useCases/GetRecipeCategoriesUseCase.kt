package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.model.RecipeCategory
import javax.inject.Inject


class GetRecipeCategoriesUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    operator fun invoke(): List<RecipeCategory> {
        return repository.getRecipeCategories()
    }
}
