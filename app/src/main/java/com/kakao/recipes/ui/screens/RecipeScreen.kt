package com.kakao.recipes.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.recipes.ui.items.CategoryItem
import com.kakao.recipes.R
import com.kakao.recipes.model.Recipe
import com.kakao.recipes.model.RecipeCategory
import com.kakao.recipes.ui.items.RecipeItem
import com.kakao.recipes.viewModels.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(viewModel: RecipeViewModel = hiltViewModel<RecipeViewModel>()) {


    val recipeCategories by viewModel.recipeCategories.collectAsState()
    val recipes by viewModel.recipes.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getRecipeCategories()
        viewModel.getRecipes()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_app), color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.hello_foodie),
                        fontSize = 28.sp,
                        color = colorResource(R.color.hello_green_color)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = stringResource(R.string.hello_question),
                        fontSize = 16.sp,
                    )
                }
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.categories),
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 18.sp
            )

            RecipeCategoriesList(viewModel, recipeCategories)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.popular_recipes),
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            RecipesList(viewModel, recipes)
        }

    }
}

@Composable
fun RecipeCategoriesList(viewModel: RecipeViewModel, recipeCategories: List<RecipeCategory>) {
    val categories = recipeCategories

    LazyRow(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(18.dp),

    ) {
        items(categories) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun RecipesList(viewModel: RecipeViewModel, recipesList: List<Recipe>) {


    LazyColumn(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color.Red)
            .fillMaxWidth(),
        //verticalArrangement = Arrangement.spacedBy(18.dp),

        ) {
        items(recipesList) { recipe ->
            RecipeItem(recipe)
        }
    }
}



@Composable
fun InfoItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text)
    }

}