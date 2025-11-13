package com.kakao.recipes

import android.content.Context
import com.kakao.recipes.domain.model.Recipe
import com.kakao.recipes.data.local.RecipeDao
import com.kakao.recipes.data.repositories.RecipeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class RecipeRepositoryUnitTest {

    lateinit var recipeDao: RecipeDao
    lateinit var context: Context
    lateinit var repository: RecipeRepository

    @Before
    fun setup() {
        context = mock(Context::class.java)
        recipeDao = mock(RecipeDao::class.java)
        repository = RecipeRepository(context, recipeDao)
    }
    @Test
    fun getRecipeCategories() {
        val list = repository.getRecipeCategories()
        assert(!list.isNullOrEmpty())
    }

    @Test
    fun insertRecipes() = runBlocking {
        val sampleRecipes = listOf(
            Recipe(
                id = 1, title = "Test Recipe 1", image = "", extendedIngredients = emptyList(),
                servings = 10,
                readyInMinutes = 60,
                summary = "",
                glutenFree = false,
                dishTypes = emptyList()
            ),
            Recipe(
                id = 2, title = "Test Recipe 2", image = "", extendedIngredients = emptyList(),
                servings = 24,
                readyInMinutes = 10,
                summary = "",
                glutenFree = true,
                dishTypes = emptyList()
            )
        )

        repository.insertRecipes(sampleRecipes)
        verify(recipeDao).insertAll(sampleRecipes)
    }
}

