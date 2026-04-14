package com.example.android.bakingapp.data

import com.example.android.bakingapp.model.Ingredient
import com.example.android.bakingapp.model.Recipe
import com.example.android.bakingapp.model.Step
import com.example.android.bakingapp.network.RecipeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepository(private val recipeService: RecipeService) {
    fun getRecipes(): Flow<List<Recipe>> = flow {
        val networkRecipes = try {
            recipeService.getRecipes()
        } catch (e: Exception) {
            emptyList()
        }

        // Enhance network recipes with images if missing
        val enhancedRecipes = networkRecipes.map { recipe ->
            if (recipe.image.isEmpty()) {
                recipe.copy(image = getImageUrlForRecipe(recipe.name))
            } else {
                recipe
            }
        }

        // Add more recipes
        val allRecipes = enhancedRecipes + getExtraRecipes()
        emit(allRecipes)
    }

    private fun getImageUrlForRecipe(name: String): String {
        return when {
            name.contains("Nutella", true) -> "https://images.unsplash.com/photo-1506459225024-1428097a7e18"
            name.contains("Brownies", true) -> "https://images.unsplash.com/photo-1461023058943-07fcbe16d735"
            name.contains("Yellow Cake", true) -> "https://images.unsplash.com/photo-1518047601542-79f18c655718"
            name.contains("Cheesecake", true) -> "https://images.unsplash.com/photo-1524350303351-799e55799035"
            else -> ""
        }
    }

    private fun getExtraRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 5,
                name = "Chocolate Cake",
                ingredients = listOf(
                    Ingredient(2.0, "CUP", "All-purpose flour"),
                    Ingredient(2.0, "CUP", "Sugar"),
                    Ingredient(0.75, "CUP", "Unsweetened cocoa powder"),
                    Ingredient(2.0, "UNIT", "Eggs")
                ),
                steps = listOf(
                    Step(0, "Introduction", "Preheat oven to 350°F (175°C).", "", ""),
                    Step(1, "Mix Dry Ingredients", "In a large bowl, stir together flour, sugar, cocoa, baking powder, baking soda and salt.", "", ""),
                    Step(2, "Add Wet Ingredients", "Add eggs, milk, oil and vanilla; beat on medium speed for 2 minutes.", "", ""),
                    Step(3, "Bake", "Bake for 30 to 35 minutes.", "", "")
                ),
                servings = 12,
                image = "https://images.unsplash.com/photo-1578985545062-69928b1d9587"
            ),
            Recipe(
                id = 6,
                name = "Apple Pie",
                ingredients = listOf(
                    Ingredient(6.0, "UNIT", "Large Granny Smith apples"),
                    Ingredient(0.75, "CUP", "Sugar"),
                    Ingredient(2.0, "TBLSP", "All-purpose flour"),
                    Ingredient(1.0, "UNIT", "Double crust pie pastry")
                ),
                steps = listOf(
                    Step(0, "Introduction", "Preheat oven to 425°F (220°C).", "", ""),
                    Step(1, "Prepare Apples", "Peel, core and slice apples.", "", ""),
                    Step(2, "Assemble", "Line pie plate with bottom crust. Fill with apple mixture.", "", ""),
                    Step(3, "Bake", "Bake for 45 minutes.", "", "")
                ),
                servings = 8,
                image = "https://images.unsplash.com/photo-1568571780765-9276ac8b75a2"
            ),
            Recipe(
                id = 7,
                name = "Blueberry Muffins",
                ingredients = listOf(
                    Ingredient(1.5, "CUP", "All-purpose flour"),
                    Ingredient(0.75, "CUP", "Sugar"),
                    Ingredient(0.5, "TSP", "Salt"),
                    Ingredient(0.33, "CUP", "Vegetable oil"),
                    Ingredient(1.0, "CUP", "Fresh blueberries")
                ),
                steps = listOf(
                    Step(0, "Introduction", "Preheat oven to 400°F (200°C).", "", ""),
                    Step(1, "Mix", "Combine flour, sugar, and salt.", "", ""),
                    Step(2, "Bake", "Bake for 20 to 25 minutes.", "", "")
                ),
                servings = 12,
                image = "https://images.unsplash.com/photo-1558303420-f814d8a590f5"
            )
        )
    }
}
