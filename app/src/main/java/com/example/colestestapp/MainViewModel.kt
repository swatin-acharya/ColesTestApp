package com.example.colestestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colestestapp.models.Recipe
import com.example.colestestapp.error.ApiStatus
import com.example.colestestapp.models.MainScreenUiState
import com.example.colestestapp.repositories.MainDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainDataRepository: MainDataRepository) :
    ViewModel() {

    private val _recipesData: MutableStateFlow<List<Recipe>> = MutableStateFlow(emptyList())
    val recipesData: StateFlow<List<Recipe>> = _recipesData

    private val _currentSelectedRecipe: MutableStateFlow<Recipe?> = MutableStateFlow(null)
    val currentSelectedRecipe: StateFlow<Recipe?> = _currentSelectedRecipe

    private val _mainUiState = MutableStateFlow(MainScreenUiState())
    val mainUiState: StateFlow<MainScreenUiState> = _mainUiState

    init {
        getRecipesDataAndAssignFirstRecipeAsSelected()
    }

    private fun getRecipesDataAndAssignFirstRecipeAsSelected() {
        viewModelScope.launch {
            mainDataRepository.getMainData().collect { result ->
                when (result.status) {
                    ApiStatus.SUCCESS -> {
                        val recipeList = result.data
                        recipeList ?: return@collect
                        _recipesData.update { recipeList }
                        updateCurrentSelectedRecipe(recipeList.firstOrNull())
                    }

                    ApiStatus.LOADING -> {
                        //unused
                    }

                    ApiStatus.ERROR -> {
                        showSnackBar(result.message)
                    }
                }
            }
        }
    }

    fun updateCurrentSelectedRecipe(recipe: Recipe?) {
        _currentSelectedRecipe.value = recipe
    }

    fun resetErrorMessageAfterShown() {
        _mainUiState.update { MainScreenUiState(errorMessage = "") }
    }

    fun showSnackBar(errorMessage: String?) {
        errorMessage?.let { _mainUiState.update { MainScreenUiState(errorMessage = errorMessage) } }
    }

}