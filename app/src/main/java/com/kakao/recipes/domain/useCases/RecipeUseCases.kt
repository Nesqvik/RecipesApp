package com.kakao.recipes.domain.useCases

data class RecipeUseCases(
    val getRecipes: GetRecipesUseCase,
    val requestRecipes: RequestRecipesUseCase,
    val loadMoreRecipes: LoadMoreRecipesUseCase,
    val searchRecipes: SearchRecipesUseCase,
    val getRecipeById: GetRecipeByIdUseCase,
    val getRecipeCategories: GetRecipeCategoriesUseCase
)
