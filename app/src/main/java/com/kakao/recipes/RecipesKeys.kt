package com.kakao.recipes


abstract class RecipesKeys {
    companion object {

        const val BASE_URL = "https://api.spoonacular.com/"
        const val RECIPES_URL = "recipes/random?number=10"
        const val API_KEY = "eb419f1ce759401299a0e2756287d86c"

        const val TITLE_RECIPE = "title"
        const val IMAGE_RECIPE = "image"
        const val SERVINGS = "servings"
        const val READY_IN_MINUTES = "readyInMinutes"

    }
}


