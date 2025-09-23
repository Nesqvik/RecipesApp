package com.kakao.recipes.apiInterfaces

import androidx.room.Query
import com.kakao.recipes.RecipesKeys
import com.kakao.recipes.model.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET


interface RecipesInterface {

    @GET(RecipesKeys.RECIPES_URL)
    suspend fun getRecipe(
    ): RecipesResponse


}

