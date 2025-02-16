package com.example.colestestapp.models

data class RecipeDetail(
    val amountLabel: String,
    val amountNumber: String,
    val prepLabel: String,
    val prepTime: String,
    val prepNote: String?,
    val cookingLabel: String,
    val cookingTime: String,
    val cookTimeAsMinutes: String,
    val prepTimeAsMinutes: String
)
