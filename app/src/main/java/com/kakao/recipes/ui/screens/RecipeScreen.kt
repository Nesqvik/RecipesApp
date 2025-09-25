package com.kakao.recipes.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kakao.recipes.ui.items.CategoryItem
import com.kakao.recipes.R
import com.kakao.recipes.data.Recipe
import com.kakao.recipes.data.RecipeCategory
import com.kakao.recipes.ui.items.RecipeItem
import com.kakao.recipes.viewModels.RecipeViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel<RecipeViewModel>(), navController: NavController,
) {

    val recipeCategories by viewModel.recipeCategories.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isNoInternet by viewModel.noInternet.collectAsState()

    val listState = rememberLazyListState()
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    val isEndReached = remember {
        derivedStateOf { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == recipes.size - 1 }
    }

    LaunchedEffect(Unit) {
        viewModel.getRecipeCategories()
        viewModel.getRecipes()


        if (isEndReached.value) {
            // viewModel.loadMoreRecipes()
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_app), color = Color.White) },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.appbar_color),
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
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.barriecito_regular)),
                        color = colorResource(R.color.hello_green_color)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = stringResource(R.string.hello_question),
                        fontSize = 16.sp,
                    )
                }
            }

            SearchBar(searchQuery = searchQuery, onSearchQueryChanged = {
                viewModel.onSearchQueryChanged(it)
            })


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
            if (searchQuery.isNotBlank() && recipes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_recipes_found),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            }

            //NO INTERNET
            if (isNoInternet && recipes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_internet_message),
                        fontSize = 19.sp
                    )
                }
            } else {
                RecipesList(viewModel, recipes, navController, { recipe ->
                    selectedRecipe = recipe
                })
            }
        }

    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { newQuery ->
            onSearchQueryChanged(newQuery)
        },
        placeholder = { Text(text = stringResource(R.string.search)) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChanged("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Gray,
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun RecipeCategoriesList(viewModel: RecipeViewModel, recipeCategories: List<RecipeCategory>) {
    val categories = recipeCategories

    LazyRow(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterHorizontally)


    ) {
        items(categories) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun RecipesList(
    viewModel: RecipeViewModel,
    recipesList: List<Recipe>,
    navController: NavController,
    onItemClick: (Recipe) -> Unit
) {

    val listState = rememberLazyListState()
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }

    /*  LaunchedEffect(listState) {
          snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
              .collect { lastVisibleIndex ->
                  if (lastVisibleIndex == recipesList.lastIndex) {
                    //  viewModel.loadNextPage()
                  }
              }
      }*/
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (
                    lastVisibleIndex != null &&
                    lastVisibleIndex >= 2 &&
                    lastVisibleIndex % 3 == 2 &&
                    !viewModel.isLoading &&
                    !viewModel.isEndReached
                ) {
                    viewModel.loadNextPage()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),

        ) {
        items(recipesList) { recipe ->
            RecipeItem(recipe = recipe, navController, onRecipeItemClick = {
                onItemClick(recipe)

            })

        }
        item {
            if (viewModel.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Black
                    )
                }
            }
        }
    }
}




