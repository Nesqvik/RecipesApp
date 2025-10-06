package com.kakao.recipes.data.apiInterfaces

import com.kakao.recipes.RecipesKeys
import com.kakao.recipes.domain.model.RecipesResponse
import retrofit2.http.GET


interface RecipesInterface {

    @GET(RecipesKeys.RECIPES_URL)
    suspend fun getRecipe(
    ): RecipesResponse

}

