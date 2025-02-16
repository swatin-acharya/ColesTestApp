package com.example.colestestapp

import com.example.colestestapp.error.ApiResult
import com.example.colestestapp.models.Ingredient
import com.example.colestestapp.models.Recipe
import com.example.colestestapp.models.RecipeDetail
import com.example.colestestapp.repositories.MainDataRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val mockRepository: MainDataRepository = mockk()


    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MainViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test successful data fetch and selection of first recipe`() {
        val mockRecipeList = listOf(
            Recipe(
                dynamicTitle = "Spaghetti",
                dynamicDescription = "D",
                dynamicThumbnail = "t",
                dynamicThumbnailAlt = "l",
                recipeDetails = RecipeDetail(
                    amountLabel = "S",
                    amountNumber = "4",
                    prepLabel = "P",
                    prepTime = "15 min",
                    prepNote = "U",
                    cookingLabel = "C",
                    cookingTime = "30 min",
                    cookTimeAsMinutes = "30",
                    prepTimeAsMinutes = "15"
                ),
                ingredients = listOf(
                    Ingredient("P"),
                    Ingredient("T")
                )
            )
        )

        coEvery { mockRepository.getMainData() } returns flowOf(ApiResult.Success(mockRecipeList))
        viewModel = MainViewModel(mockRepository)
        assertEquals(mockRecipeList, viewModel.recipesData.value)
        assertEquals(mockRecipeList.first(), viewModel.currentSelectedRecipe.value)
    }

    @Test
    fun `test empty recipe list`() {
        coEvery { mockRepository.getMainData() } returns flowOf(ApiResult.Success(emptyList()))
        viewModel = MainViewModel(mockRepository)
        assertEquals(emptyList(), viewModel.recipesData.value)
        assertNull(viewModel.currentSelectedRecipe.value)
    }

    @Test
    fun `test error while fetching data`() {
        val errorMessage = "Network error"
        coEvery { mockRepository.getMainData() } returns flowOf(ApiResult.Error(errorMessage))
        viewModel = MainViewModel(mockRepository)
        assertEquals(errorMessage, viewModel.mainUiState.value.errorMessage)
    }

    @Test
    fun `test resetErrorMessageAfterShown`() {
        viewModel.showSnackBar("Some error occurred")
        viewModel.resetErrorMessageAfterShown()
        assertEquals("", viewModel.mainUiState.value.errorMessage)
    }

    @Test
    fun `test showSnackBar with error message`() {
        val errorMessage = "Network error"
        viewModel.showSnackBar(errorMessage)
        assertEquals(errorMessage, viewModel.mainUiState.value.errorMessage)
    }

    @Test
    fun `test updateCurrentSelectedRecipe`() {
        val sampleRecipe = Recipe(
            dynamicTitle = "Spaghetti",
            dynamicDescription = "D.",
            dynamicThumbnail = "t",
            dynamicThumbnailAlt = "l",
            recipeDetails = RecipeDetail(
                amountLabel = "S",
                amountNumber = "4",
                prepLabel = "P",
                prepTime = "15 min",
                prepNote = "U",
                cookingLabel = "C",
                cookingTime = "30 min",
                cookTimeAsMinutes = "30",
                prepTimeAsMinutes = "15"
            ),
            ingredients = listOf(
                Ingredient("P"),
                Ingredient("T")
            )
        )
        viewModel.updateCurrentSelectedRecipe(sampleRecipe)
        assertEquals(sampleRecipe, viewModel.currentSelectedRecipe.value)
    }
}
