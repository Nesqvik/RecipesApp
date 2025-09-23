package com.kakao.recipes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kakao.recipes.RecipesKeys


@Entity
data class Recipe(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey val id: Int,

    @SerializedName(RecipesKeys.TITLE_RECIPE)
    var title: String = "",

    @SerializedName(RecipesKeys.IMAGE_RECIPE)
    var image: String = ""

)