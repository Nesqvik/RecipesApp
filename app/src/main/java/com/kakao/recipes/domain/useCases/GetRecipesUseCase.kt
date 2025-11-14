package com.kakao.recipes.domain.useCases

import android.util.Log
import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import com.kakao.recipes.core.util.Result

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepositoryInterface
) {
    operator fun invoke(): Flow<Result<List<Recipe>>> = flow {
        emit(Result.Loading)

        val local = repository.getRecipes().firstOrNull()
        if (local.isNullOrEmpty()) {

            val result = repository.requestRecipes()
            if (result.isFailure) {
                emit(Result.Error(result.exceptionOrNull()))
                return@flow
            }
        }
        repository.getRecipes()
            .collect { recipes ->
                emit(Result.Success(recipes))
            }

    }.catch { e ->
        emit(Result.Error(e))
    }
}


