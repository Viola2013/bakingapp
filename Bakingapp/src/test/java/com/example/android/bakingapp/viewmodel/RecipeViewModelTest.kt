package com.example.android.bakingapp.viewmodel

import com.example.android.bakingapp.data.RecipeRepository
import com.example.android.bakingapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class RecipeViewModelTest {

    private lateinit var viewModel: RecipeViewModel
    private val repository: RecipeRepository = mock(RecipeRepository::class.java)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchRecipes_updatesUiStateWithSuccess_whenRepositoryReturnsData() = runTest {
        val recipes = listOf(
            Recipe(1, "Nutella Pie", emptyList(), emptyList(), 8, "")
        )
        `when`(repository.getRecipes()).thenReturn(flowOf(recipes))

        viewModel = RecipeViewModel(repository)
        advanceUntilIdle()

        val expectedState = RecipeUiState.Success(recipes)
        assertEquals(expectedState, viewModel.uiState.value)
    }
}
