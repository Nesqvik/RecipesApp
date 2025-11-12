package com.kakao.recipes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kakao.recipes.data.local.Converters
import com.kakao.recipes.domain.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
