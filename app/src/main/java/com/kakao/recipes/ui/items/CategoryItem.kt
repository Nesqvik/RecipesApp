package com.kakao.recipes.ui.items

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kakao.recipes.R
import com.kakao.recipes.data.RecipeCategory
import com.kakao.recipes.viewModels.RecipeViewModel


@Composable
fun CategoryItem(category: RecipeCategory, viewModel: RecipeViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            viewModel.filterBySelectedCategory(category.name)
            if (category.name == "Show all") {
                viewModel.resetFilters()
            }
        }
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .border(
                    1.dp,
                    color = colorResource(R.color.recipe_category_border_color),
                    CircleShape
                )
                .background(colorResource(id = R.color.recipe_category_back_color)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name.take(2).uppercase(),
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.lato_regular))
        )
    }
}

