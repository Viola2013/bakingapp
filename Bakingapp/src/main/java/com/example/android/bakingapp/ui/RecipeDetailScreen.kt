package com.example.android.bakingapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.android.bakingapp.R
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.android.bakingapp.data.RecipePreferences
import com.example.android.bakingapp.model.Recipe
import com.example.android.bakingapp.model.Step

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onStepClick: (Step) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(recipe) {
        RecipePreferences.saveIngredients(context, recipe.name, recipe.ingredients)
        // Notify widget to update
        val intent = android.content.Intent(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        context.sendBroadcast(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.name) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.semantics { contentDescription = "Back" }
                    ) {
                        Text("<") // Placeholder for back icon
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.ingredients_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(recipe.ingredients) { ingredient ->
                Text(
                    text = "• ${ingredient.quantity} ${ingredient.measure} ${ingredient.ingredient}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.steps_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(recipe.steps) { step ->
                StepItem(step = step, onClick = { onStepClick(step) })
            }
        }
    }
}

@Composable
fun StepItem(step: Step, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = "${step.id}. ${step.shortDescription}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
