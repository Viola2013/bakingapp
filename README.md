# Baking App

An Android application developed as part of the Udacity Android Developer Nanodegree. This app provides users with recipes for various desserts, including step-by-step instructions and video demonstrations.

## Features

- **Intro Page**: A beautiful onboarding screen that welcomes users and provides a quick "Get Started" entry into the app.
- **Recipe List**: Browse through an expanded variety of delicious dessert recipes with high-quality imagery.
- **Recipe Details**: View comprehensive ingredient lists and detailed step-by-step instructions for each recipe.
- **Video Instructions**: Watch step-by-step video guides for recipe preparation using ExoPlayer.
- **Home Screen Widget**: Quick access to ingredients for the last viewed recipe directly from your home screen.
- **Responsive Design**: Optimized for both phone and tablet layouts.
- **Modern UI**: Built with Jetpack Compose and Material 3.
- **Adaptive Launcher Icon**: Updated icon with support for standard, round, and monochrome themed styles.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit & Gson
- **Image Loading**: Coil
- **Video Playback**: ExoPlayer (Media3)
- **Dependency Injection**: Manual / Clean Architecture principles
- **Navigation**: Compose Navigation
- **SDK**: Target and Compile SDK 36 (Android 16 compatibility)

## Project Structure

- `ui/`: Contains all Compose screens and components, including the new `IntroScreen.kt`.
- `viewmodel/`: Logic for managing UI state and interacting with data sources.
- `model/`: Data classes and network models.
- `data/`: Contains `RecipeRepository.kt` with expanded local recipe data and image mapping logic.
- `network/`: Retrofit service definitions for fetching recipe data.
- `widget/`: Implementation of the App Widget.

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/bakingapp.git
   ```
2. Open the project in Android Studio (Meerkat 2024.3.1 or newer recommended for full API 36 support).
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device (Android API 24+).

## Recent Updates

- **Modernization**: Upgraded the project to target Android API 36 to support the latest `media3` and `androidx.core` libraries.
- **UI Enhancements**: Added a dedicated `IntroScreen` as the start destination.
- **Content Expansion**: Added new recipes (Chocolate Cake, Apple Pie, Blueberry Muffins) and integrated high-quality Unsplash images for all items.
- **Icon Update**: Redesigned launcher icons with adaptive and monochrome support using a new baking-themed vector asset.

## Automated Testing

This project includes a comprehensive testing infrastructure:

### Native Android Tests
- **Smoke Tests**: Verifies app launch and onboarding.
- **Integration Tests**: Validates data flow between ViewModel and Repository.
- **System & Regression**: End-to-end user journeys and feature verification.
- Location: `src/androidTest/` and `src/test/`

### Robot Framework (Appium)
A structured suite of behavior-driven tests organized for sequential execution:
- `01_smoke`: Essential launch checks.
- `02_integration`: UI-to-Data bridge verification.
- `03_system`: Complex user interaction paths.
- `04_regression`: Verification of new content and fixes.
- `05_e2e`: Complete End-to-End scenarios.

**To run Robot tests:**
1. Start Appium server.
2. Ensure an emulator/device is connected.
3. Run: `robot robot_tests/`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
