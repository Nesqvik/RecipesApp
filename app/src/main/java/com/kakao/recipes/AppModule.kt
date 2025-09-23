package com.kakao.recipes

import android.content.Context
import com.kakao.recipes.interfaces.RecipeRepositoryInterface
import com.kakao.recipes.repositories.RecipeRepository
import com.kakao.recipes.viewModels.RecipeViewModel
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
    fun provideMemberRepository(
        context: Context
    ): RecipeRepositoryInterface {
        return RecipeRepository(context)
    }

    @Provides
    fun provideUserViewModel(recipeRepositoryInterface: RecipeRepositoryInterface) =
        RecipeViewModel(recipeRepositoryInterface)
}

