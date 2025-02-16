package com.example.colestestapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colestestapp.api.getFullUrl
import com.example.colestestapp.models.Recipe
import com.example.colestestapp.ui.FullPageRecipeView
import com.example.colestestapp.ui.GridPhotoItemWithPhotoHeaderAndText
import com.example.colestestapp.ui.LargeImageView
import com.example.colestestapp.ui.NavigationItem
import com.example.colestestapp.ui.theme.ColesTestAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val configuration = LocalConfiguration.current

            val recipesData = mainViewModel.recipesData.collectAsState()
            val currentRecipe = mainViewModel.currentSelectedRecipe.collectAsState()
            val snackBarMessage = mainViewModel.mainUiState.collectAsState().value.errorMessage

            val scope = rememberCoroutineScope()
            val snackBarHostState = remember { SnackbarHostState() }

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
            ) { padding ->
                ShowErrorSnackBarIfNecessary(snackBarMessage, scope, snackBarHostState) {
                    mainViewModel.resetErrorMessageAfterShown()
                }
                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        LandscapeView(
                            recipesData.value,
                            currentRecipe.value,
                            padding
                        ) { clickedRecipe ->
                            mainViewModel.updateCurrentSelectedRecipe(
                                clickedRecipe
                            )
                        }
                    }

                    else -> {
                        currentRecipe.value?.let {
                            val navController = rememberNavController()
                            CreateNavHostAndController(it, padding, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateNavHostAndController(
    currentRecipe: Recipe,
    padding: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.PortraitScreen.route
    ) {
        composable(NavigationItem.PortraitScreen.route) {
            FullPageRecipeView(currentRecipe, padding, navController)
        }
        composable(NavigationItem.ImageScreen.route) {
            LargeImageView(
                navController,
                getFullUrl(currentRecipe.dynamicThumbnail),
                currentRecipe.dynamicThumbnailAlt
            )
        }
    }
}

@Composable
fun ShowErrorSnackBarIfNecessary(
    snackBarMessage: String,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    snackBarShown: () -> Unit
) {
    if (snackBarMessage.isNotBlank()) {
        LaunchedEffect(key1 = snackBarMessage) {
            scope.launch {
                snackBarHostState.showSnackbar(snackBarMessage)
                snackBarShown()
            }
        }
    }
}

@Composable
fun LandscapeView(
    recipesList: List<Recipe>,
    currentRecipe: Recipe?,
    padding: PaddingValues,
    onClickItem: (recipe: Recipe) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier.padding(padding)
    ) {
        items(recipesList) { recipe ->
            GridPhotoItem(recipe, recipe == currentRecipe) { clickedRecipe ->
                onClickItem(
                    clickedRecipe
                )
            }
        }
    }
}

@Composable
fun GridPhotoItem(recipe: Recipe, isCurrentRecipe: Boolean, onClick: (recipe: Recipe) -> Unit) {
    GridPhotoItemWithPhotoHeaderAndText(
        getFullUrl(recipe.dynamicThumbnail),
        recipe.dynamicThumbnailAlt,
        recipe.dynamicTitle,
        recipe.dynamicDescription,
        isCurrentRecipe,
    ) { onClick(recipe) }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColesTestAppTheme {
        Greeting("Android")
    }
}