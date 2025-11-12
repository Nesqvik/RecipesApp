package com.kakao.recipes.core.util

abstract class RecipesKeys {
    companion object {

        const val BASE_URL = "https://api.spoonacular.com/"
        const val RECIPES_URL = "recipes/random?number=1&include-tags=vegetarian,dessert"
        const val API_KEY = "5c7e91946c9d446b8c6ae1aa1cf903da"

        const val TITLE_RECIPE = "title"
        const val IMAGE_RECIPE = "image"
        const val SERVINGS = "servings"
        const val READY_IN_MINUTES = "readyInMinutes"
        const val SUMMARY = "summary"
        const val EXTENDED_INGREDIENTS = "extendedIngredients"
        const val GLUTEN_FREE = "glutenFree"
        const val DISH_TYPE = "dishTypes"

        const val MAIN_COURSE = 1
        const val SIDE_DISH = 2
        const val DESSERT = 3
        const val APPETIZER = 4
        const val SALAD = 5

    }
}