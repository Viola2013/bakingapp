package com.example.android.bakingapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * SMOKE TEST SUITE
 * Fast, essential checks to ensure the app launches and basic elements are present.
 */
@RunWith(AndroidJUnit4::class)
class BakingSmokeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunch_showsIntroScreen() {
        // Verify Intro Screen content is visible immediately
        composeTestRule.onNodeWithText("Welcome to Baking App!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()
    }
}
