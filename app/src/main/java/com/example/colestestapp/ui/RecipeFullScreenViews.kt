package com.example.colestestapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.colestestapp.R
import com.example.colestestapp.api.getFullUrl
import com.example.colestestapp.models.Ingredient
import com.example.colestestapp.models.Recipe

@Composable
fun FullPageRecipeView(
    currentRecipe: Recipe,
    padding: PaddingValues,
    navController: NavController
) {
    Box(Modifier.padding(padding)) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 30.dp)
        ) {
            Spacer(Modifier.height(70.dp))
            Text(text = currentRecipe.dynamicTitle, style = portraitHeaderTextStyle)
            Spacer(Modifier.height(30.dp))
            Text(text = currentRecipe.dynamicDescription, style = portraitDescriptionTextStyle)
            Spacer(Modifier.height(30.dp))
            AsyncImage(
                model = getFullUrl(currentRecipe.dynamicThumbnail),
                contentDescription = currentRecipe.dynamicThumbnailAlt,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(150.dp)
                    .clickable { navController.navigate(NavigationItem.ImageScreen.route) }
            )
            Spacer(Modifier.height(30.dp))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = Color.LightGray
            )
            Spacer(Modifier.height(10.dp))
            TimeValueRow(currentRecipe)
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = Color.LightGray
            )
            Spacer(Modifier.height(40.dp))
            Text(text = stringResource(R.string.ingredients), style = portraitIngredientTextStyle)
            Spacer(Modifier.height(30.dp))
            currentRecipe.ingredients.forEach { ingredient ->
                BulletText(ingredient)
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.height(50.dp))
        }
    }
}

@Composable
fun TimeValueRow(currentRecipe: Recipe) {
    Row(horizontalArrangement = Arrangement.Center) {
        Column(
            Modifier
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = currentRecipe.recipeDetails.amountLabel)
            Spacer(Modifier.height(5.dp))
            Text(text = currentRecipe.recipeDetails.amountNumber)
        }

        Column(
            Modifier
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = currentRecipe.recipeDetails.prepLabel)
            Spacer(Modifier.height(5.dp))
            Text(text = currentRecipe.recipeDetails.prepTime)
        }
        Column(
            Modifier
                .wrapContentHeight()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = currentRecipe.recipeDetails.cookingLabel)
            Spacer(Modifier.height(5.dp))
            Text(text = currentRecipe.recipeDetails.cookingTime)
        }
    }
}

@Composable
fun BulletText(ingredient: Ingredient) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(text = ">", style = ingredientBulletRowTextStyle)
        Spacer(Modifier.width(10.dp))
        Text(text = ingredient.ingredient, style = ingredientBulletRowTextStyle)
    }
}