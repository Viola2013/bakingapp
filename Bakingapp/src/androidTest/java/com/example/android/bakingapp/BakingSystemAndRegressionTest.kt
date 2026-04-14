package com.example.android.bakingapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * SYSTEM & REGRESSION TEST SUITE
 * End-to-end user journeys and checks for regressions on recently updated features.
 */
@RunWith(AndroidJUnit4::class)
class BakingSystemAndRegressionTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun fullUserJourney_FromIntroToRecipeDetail() {
        // 1. SMOKE: Verify launch
        composeTestRule.onNodeWithText("Welcome to Baking App!").assertIsDisplayed()

        // 2. INTERACTION: Get Started
        composeTestRule.onNodeWithText("Get Started").performClick()

        // 3. REGRESSION: Verify recipe list is loaded (wait for newly added recipe)
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Chocolate Cake").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Chocolate Cake").assertIsDisplayed()

        // 4. NAVIGATION: Go to details
        composeTestRule.onNodeWithText("Chocolate Cake").performClick()

        // 5. VERIFICATION: Ensure detailed content is present
        composeTestRule.onNodeWithText("Ingredients").assertIsDisplayed()
        composeTestRule.onNodeWithText("All-purpose flour").assertIsDisplayed()

        // 6. BACK NAVIGATION: Return to list
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Chocolate Cake").assertIsDisplayed()
    }

    @Test
    fun regression_newlyAddedRecipesArePresent() {
        composeTestRule.onNodeWithText("Get Started").performClick()
        
        // Wait and check for all 3 newly added recipes
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Blueberry Muffins").fetchSemanticsNodes().isNotEmpty()
        }
        
        composeTestRule.onNodeWithText("Apple Pie").assertIsDisplayed()
        composeTestRule.onNodeWithText("Blueberry Muffins").assertIsDisplayed()
    }
}
