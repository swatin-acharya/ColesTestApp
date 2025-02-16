package com.example.colestestapp.models

data class Recipe(
    val dynamicTitle: String,
    val dynamicDescription: String,
    val dynamicThumbnail: String,
    val dynamicThumbnailAlt: String,
    val recipeDetails: RecipeDetail,
    val ingredients: List<Ingredient>,
)
