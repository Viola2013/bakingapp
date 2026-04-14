package com.example.android.bakingapp

import com.example.android.bakingapp.data.RecipeRepository
import com.example.android.bakingapp.model.Recipe
import com.example.android.bakingapp.network.RecipeService
import com.example.android.bakingapp.viewmodel.RecipeUiState
import com.example.android.bakingapp.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * INTEGRATION TEST SUITE
 * Verifies the interaction between ViewModel, Repository, and Data mapping.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class BakingIntegrationTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: RecipeRepository
    private val mockService = mock(RecipeService::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = RecipeRepository(mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Repository Integration - verify image mapping and extra recipes`() = runTest {
        // Arrange
        `when`(mockService.getRecipes()).thenReturn(listOf(
            Recipe(1, "Nutella Pie", emptyList(), emptyList(), 8, "")
        ))

        // Act
        val recipes = repository.getRecipes().first()

        // Assert
        assertTrue("Should contain network recipes", recipes.any { it.name == "Nutella Pie" })
        assertTrue("Should map image for Nutella Pie", recipes.find { it.name == "Nutella Pie" }?.image?.isNotEmpty() == true)
        assertTrue("Should contain Chocolate Cake", recipes.any { it.name == "Chocolate Cake" })
        assertEquals("Total recipes should be network + 3 extra", 4, recipes.size)
    }

    @Test
    fun `ViewModel Integration - state transitions from loading to success`() = runTest {
        // Arrange
        `when`(mockService.getRecipes()).thenReturn(emptyList())
        val viewModel = RecipeViewModel(repository)

        // Act & Assert
        // Initial state
        assertTrue(viewModel.uiState.value is RecipeUiState.Loading)
        
        advanceUntilIdle()
        
        // Final state
        val state = viewModel.uiState.value
        assertTrue("State should be Success", state is RecipeUiState.Success)
        val successState = state as RecipeUiState.Success
        assertEquals(3, successState.recipes.size) // Only the 3 extra ones since network returned empty
    }
}
