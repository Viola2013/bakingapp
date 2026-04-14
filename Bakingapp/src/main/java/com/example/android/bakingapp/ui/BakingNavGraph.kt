package com.example.android.bakingapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.bakingapp.data.RecipeRepository
import com.example.android.bakingapp.network.RetrofitClient
import com.example.android.bakingapp.viewmodel.RecipeUiState
import com.example.android.bakingapp.viewmodel.RecipeViewModel
import com.example.android.bakingapp.viewmodel.ViewModelFactory

sealed class Screen(val route: String) {
    object Intro : Screen("intro")
    object RecipeList : Screen("recipe_list")
    object RecipeDetail : Screen("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe_detail/$recipeId"
    }
    object StepDetail : Screen("step_detail/{recipeId}/{stepId}") {
        fun createRoute(recipeId: Int, stepId: Int) = "step_detail/$recipeId/$stepId"
    }
}

@Composable
fun BakingNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: RecipeViewModel = viewModel(
        factory = ViewModelFactory(RecipeRepository(RetrofitClient.recipeService))
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Intro.route
    ) {
        composable(Screen.Intro.route) {
            IntroScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.RecipeList.route) {
                        popUpTo(Screen.Intro.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.RecipeList.route) {
            RecipeListScreen(
                uiState = uiState,
                onRecipeClick = { recipe ->
                    navController.navigate(Screen.RecipeDetail.createRoute(recipe.id))
                },
                onRetry = { viewModel.fetchRecipes() }
            )
        }

        composable(Screen.RecipeDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            val state = uiState
            if (state is RecipeUiState.Success) {
                val recipe = state.recipes.find { it.id == recipeId }
                recipe?.let {
                    RecipeDetailScreen(
                        recipe = it,
                        onStepClick = { step ->
                            navController.navigate(Screen.StepDetail.createRoute(it.id, step.id))
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(Screen.StepDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            val stepId = backStackEntry.arguments?.getString("stepId")?.toIntOrNull()
            val state = uiState
            if (state is RecipeUiState.Success) {
                val recipe = state.recipes.find { it.id == recipeId }
                val step = recipe?.steps?.find { it.id == stepId }
                step?.let {
                    StepDetailScreen(
                        step = it,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
