package com.example.android.bakingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.bakingapp.data.RecipeRepository
import com.example.android.bakingapp.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes: List<Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
}

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        fetchRecipes()
    }

    fun fetchRecipes() {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            repository.getRecipes()
                .catch { e ->
                    _uiState.value = RecipeUiState.Error(e.message ?: "Unknown Error")
                }
                .collect { recipes ->
                    _uiState.value = RecipeUiState.Success(recipes)
                }
        }
    }
}
