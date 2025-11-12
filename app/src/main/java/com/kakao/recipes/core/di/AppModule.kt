package com.kakao.recipes.core.di

import android.content.Context
import com.kakao.recipes.data.local.AppDatabase
import com.kakao.recipes.data.local.RecipeDao
import com.kakao.recipes.data.repositories.RecipeRepository
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.useCases.GetRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.Companion.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao {
        return appDatabase.recipeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        context: Context,
        recipeDao: RecipeDao
    ): RecipeRepositoryInterface {
        return RecipeRepository(context, recipeDao)
    }

    @Provides
    @Singleton
    fun provideGetRecipesUseCase(recipeRepositoryInterface: RecipeRepositoryInterface): GetRecipesUseCase {
        return GetRecipesUseCase(recipeRepositoryInterface)
    }

}