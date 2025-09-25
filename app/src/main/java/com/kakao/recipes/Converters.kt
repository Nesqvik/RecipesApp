package com.kakao.recipes

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kakao.recipes.data.ExtendedIngredient

class Converters {
    @TypeConverter
    fun fromIngredientsList(value: List<ExtendedIngredient>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<ExtendedIngredient> {
        val listType = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
