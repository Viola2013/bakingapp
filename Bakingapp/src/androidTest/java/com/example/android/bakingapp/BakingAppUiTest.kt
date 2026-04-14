package com.example.android.bakingapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI Test for the Baking App using Compose Test Rule.
 * Since the app uses Jetpack Compose, we use the Compose Testing library
 * which provides more reliable interactions with Compose UI elements.
 */
@RunWith(AndroidJUnit4::class)
class BakingAppUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testRecipeListDisplayed() {
        // Wait for recipes to load (handle potential network delay)
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodes(
                androidx.compose.ui.test.hasText("Nutella Pie")
            ).fetchSemanticsNodes().isNotEmpty()
        }

        // Verify "Nutella Pie" is displayed
        composeTestRule.onNodeWithText("Nutella Pie").assertIsDisplayed()
    }

    @Test
    fun testNavigateToRecipeDetail() {
        // Wait for "Nutella Pie" and click on it
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodes(
                androidx.compose.ui.test.hasText("Nutella Pie")
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Nutella Pie").performClick()

        // Verify that the ingredients title is shown on the detail screen
        composeTestRule.onNodeWithText("Ingredients").assertIsDisplayed()
    }

    @Test
    fun testNavigateToStepDetail() {
        // Go to Nutella Pie detail screen
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodes(
                androidx.compose.ui.test.hasText("Nutella Pie")
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Nutella Pie").performClick()

        // Click on the first step
        composeTestRule.onNodeWithText("0. Recipe Introduction").performClick()

        // Verify step detail screen is shown (check for "Step 0" title)
        composeTestRule.onNodeWithText("Step 0").assertIsDisplayed()
    }
}
