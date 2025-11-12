package com.kakao.recipes.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kakao.recipes.domain.model.ExtendedIngredient

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


    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}