package com.kakao.recipes.core.di

import android.content.Context
import com.kakao.recipes.data.local.AppDatabase
import com.kakao.recipes.data.local.RecipeDao
import com.kakao.recipes.data.repositories.RecipeRepository
import com.kakao.recipes.domain.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.domain.useCases.GetRecipeByIdUseCase
import com.kakao.recipes.domain.useCases.GetRecipeCategoriesUseCase
import com.kakao.recipes.domain.useCases.GetRecipesUseCase
import com.kakao.recipes.domain.useCases.LoadMoreRecipesUseCase
import com.kakao.recipes.domain.useCases.RecipeUseCases
import com.kakao.recipes.domain.useCases.RequestRecipesUseCase
import com.kakao.recipes.domain.useCases.SearchRecipesUseCase
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

    /* @Provides
     @Singleton
     fun provideGetRecipesUseCase(recipeRepositoryInterface: RecipeRepositoryInterface): GetRecipesUseCase {
         return GetRecipesUseCase(recipeRepositoryInterface)
     }*/

    @Provides
    @Singleton
    fun provideRecipeUseCases(recipeRepositoryInterface: RecipeRepositoryInterface): RecipeUseCases {
        return RecipeUseCases(
            getRecipes = GetRecipesUseCase(recipeRepositoryInterface),
            requestRecipes = RequestRecipesUseCase(recipeRepositoryInterface),
            loadMoreRecipes = LoadMoreRecipesUseCase(recipeRepositoryInterface),
            searchRecipes = SearchRecipesUseCase(recipeRepositoryInterface),
            getRecipeById = GetRecipeByIdUseCase(recipeRepositoryInterface),
            getRecipeCategories = GetRecipeCategoriesUseCase(recipeRepositoryInterface)
        )
    }

}