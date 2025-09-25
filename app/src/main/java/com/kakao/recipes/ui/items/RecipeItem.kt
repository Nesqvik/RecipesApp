package com.kakao.recipes.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kakao.recipes.R
import com.kakao.recipes.data.Recipe


@Composable
fun RecipeItem(recipe: Recipe, navController: NavController, onRecipeItemClick: (Recipe) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.navigate("recipeDetail/${recipe.id}")
                onRecipeItemClick(recipe)
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = recipe.title,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    )

                if(recipe.glutenFree) {
                    Text("[gluten free]")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoItem(
                        icon = R.drawable.outline_person,
                        text = recipe.servings.toString() + " Servings"
                    )
                    InfoItem(
                        icon = R.drawable.outline_clock_loader_60_24,
                        text = recipe.readyInMinutes.toString() + " Minutes"
                    )
                    InfoItem(icon = R.drawable.outline_favorite_24, text = "3 Likes")
                }
            }
        }
    }
}


@Composable
fun InfoItem(icon: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(1.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(end = 3.dp)
        )
        Text(
            text = text,
            color = Color.Black,
            fontSize = 15.sp
        )
    }

}

