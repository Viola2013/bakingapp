package com.example.android.bakingapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.bakingapp.R
import com.example.android.bakingapp.model.Ingredient
import com.example.android.bakingapp.model.Recipe
import com.example.android.bakingapp.model.Step
import com.example.android.bakingapp.ui.theme.BakingAppTheme
import com.example.android.bakingapp.viewmodel.RecipeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    uiState: RecipeUiState,
    onRecipeClick: (Recipe) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.recipe_list_title)) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (uiState) {
                is RecipeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is RecipeUiState.Success -> {
                    RecipeGrid(recipes = uiState.recipes, onRecipeClick = onRecipeClick)
                }
                is RecipeUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeGrid(recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            if (recipe.image.isNotEmpty()) {
                AsyncImage(
                    model = recipe.image,
                    contentDescription = recipe.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Servings: ${recipe.servings}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListPreview() {
    val mockRecipes = listOf(
        Recipe(
            id = 1,
            name = "Nutella Pie",
            ingredients = listOf(
                Ingredient(2.0, "CUP", "Graham Cracker crumbs"),
                Ingredient(6.0, "TBLSP", "Butter, melted")
            ),
            steps = listOf(
                Step(0, "Recipe Introduction", "Recipe Introduction", "", "")
            ),
            servings = 8,
            image = ""
        ),
        Recipe(
            id = 2,
            name = "Brownies",
            ingredients = emptyList(),
            steps = emptyList(),
            servings = 12,
            image = ""
        )
    )
    BakingAppTheme {
        RecipeListScreen(
            uiState = RecipeUiState.Success(mockRecipes),
            onRecipeClick = {},
            onRetry = {}
        )
    }
}
