package com.kakao.recipes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kakao.recipes.RecipesKeys


@Entity(tableName = "recipes")
data class Recipe(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey val id: Int,

    @SerializedName(RecipesKeys.TITLE_RECIPE)
    var title: String = "",

    @SerializedName(RecipesKeys.IMAGE_RECIPE)
    var image: String = "",

    @SerializedName(RecipesKeys.SERVINGS)
    var servings: Int = 0,

    @SerializedName(RecipesKeys.READY_IN_MINUTES)
    var readyInMinutes: Int = 0,

    @SerializedName(RecipesKeys.SUMMARY)
    var summary: String = "",

    @SerializedName(RecipesKeys.GLUTEN_FREE)
    var glutenFree: Boolean = false,


    @SerializedName(RecipesKeys.EXTENDED_INGREDIENTS)
    var extendedIngredients: List<ExtendedIngredient>


)