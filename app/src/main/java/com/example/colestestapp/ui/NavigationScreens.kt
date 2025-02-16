package com.example.colestestapp.ui

enum class Screen {
    PORTRAIT_SCREEN,
    IMAGE_SCREEN,
}
sealed class NavigationItem(val route: String) {
    object PortraitScreen : NavigationItem(Screen.PORTRAIT_SCREEN.name)
    object ImageScreen : NavigationItem(Screen.IMAGE_SCREEN.name)
}