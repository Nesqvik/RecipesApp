package com.kakao.recipes

import android.content.Context
import com.kakao.recipes.data.Recipe
import com.kakao.recipes.data.RecipeCategory
import com.kakao.recipes.local.RecipeDao
import com.kakao.recipes.repositories.RecipeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
        val categories = repository.getRecipeCategories()

        val expected = listOf(
            RecipeCategory(1, "Main Course"),
            RecipeCategory(2, "Side Dish"),
        )

        assertEquals(expected, categories)
    }

    @Test
    fun insertRecipes() = runBlocking {
        val sampleRecipes = listOf(
            Recipe(id = 1, title = "Test Recipe 1", image = "", extendedIngredients = emptyList()),
            Recipe(id = 2, title = "Test Recipe 2", image = "", extendedIngredients = emptyList())
        )
        repository.recipesList.addAll(sampleRecipes)
        repository.insertRecipes()
        verify(recipeDao).insertAll(sampleRecipes)
    }
}

