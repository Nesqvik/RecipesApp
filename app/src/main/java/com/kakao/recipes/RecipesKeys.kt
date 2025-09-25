package com.kakao.recipes


abstract class RecipesKeys {
    companion object {

        const val BASE_URL = "https://api.spoonacular.com/"
        const val RECIPES_URL = "recipes/random?number=1&include-tags=vegetarian,dessert"
        const val API_KEY = "de541060bc614b8abe8259ea4743b75f"

        const val TITLE_RECIPE = "title"
        const val IMAGE_RECIPE = "image"
        const val SERVINGS = "servings"
        const val READY_IN_MINUTES = "readyInMinutes"
        const val SUMMARY = "summary"
        const val EXTENDED_INGREDIENTS = "extendedIngredients"
        const val GLUTEN_FREE = "glutenFree"

    }
}


