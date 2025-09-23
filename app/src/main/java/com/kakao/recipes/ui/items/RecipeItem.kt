package com.kakao.recipes.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakao.recipes.R
import com.kakao.recipes.ui.screens.InfoItem
import androidx.compose.foundation.shape.RoundedCornerShape
import com.kakao.recipes.model.Recipe


@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
    modifier = Modifier
    .padding(16.dp)
    .fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    //elevation = 4.dp
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.rrrrr),
                contentDescription = "Recipe",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = recipe.title
                )
                Text("[gluten free]")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoItem(icon = Icons.Default.Person, text = "8 Servings")
                    InfoItem(icon = Icons.Default.Person, text = "45 Minutes")
                    InfoItem(icon = Icons.Default.Favorite, text = "3 Likes")
                }
            }
        }
    }
}