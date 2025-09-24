package com.kakao.recipes.apiInterfaces

import com.kakao.recipes.RecipesKeys
import com.kakao.recipes.data.RecipesResponse
import retrofit2.http.GET


interface RecipesInterface {

    @GET(RecipesKeys.RECIPES_URL)
    suspend fun getRecipe(
    ): RecipesResponse

}

