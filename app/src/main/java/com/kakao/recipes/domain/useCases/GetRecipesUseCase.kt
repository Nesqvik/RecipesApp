package com.kakao.recipes.domain.useCases

import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import com.kakao.recipes.core.util.Result
import android.util.Log

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    operator fun invoke(query: String): Flow<Result<List<Recipe>>> = flow {
        emit(Result.Loading)

        val local = repository.getRecipes().firstOrNull()
        if (local.isNullOrEmpty()) {

            val result = repository.requestRecipes()
            if (result.isFailure) {
                emit(Result.Error(result.exceptionOrNull()))
                return@flow
            }
        }
        if(query.isEmpty()) {
            repository.getRecipes()
                .collect { recipes ->
                    emit(Result.Success(recipes))
                }
        }
        else {
            repository.searchRecipesByName(query)
                .collect { recipes ->
                    emit(Result.Success(recipes))
                }
        }

    }.catch { e ->
        emit(Result.Error(e))
    }
}


