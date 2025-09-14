# ScreenNameViewer-For-Compose

**[한국어 README](./README_ko.md)**

A lightweight Android debug library that displays Activity and Fragment class names as screen overlays during development.

## Overview

![sample](https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose/blob/eae99cecc086002a6958e12620ec80647c89822f/.github/docs/images/screennameviewer-compose-exmaple.png)
An extension of the existing XML ScreenNameViewer that displays Compose Screen names as overlays as well.

This library is particularly useful for Single Activity architectures or when managing multiple Screens within Activities/Fragments.

ScreenNameViewer displays the class names of currently visible Activities and Fragments in real-time on screen.

It significantly improves debugging and development efficiency in apps with complex Fragment structures or frequent screen transitions.

## Features

- **Real-time class name display**: Shows Activity and Fragment class names on screen in real-time
- **Automatic lifecycle management**: Automatically tracks all Activities and Fragments at the Application level
- **Debug-only**: Automatically disabled in release builds for safety
- **UI customization**: Freely configure text size, color, position, etc.
- **Memory safe**: Prevents memory leaks using WeakReference
- **Touch interaction**: Touch overlay to show full class name in toast

## Installation

### Step 1: Add GitHub Packages repository

Add the GitHub Packages repository to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/DongLab-DevTools/ScreenNameViewer-For-Compose")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}
```

### Step 2: Add dependency

Add the library to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation 'com.donglab.devtools:screennameviewer-compose:latestVersion'
}
```

### Step 3: Authentication

Create a `gradle.properties` file in your project root with your GitHub credentials:

```properties
gpr.user=YOUR_GITHUB_USERNAME
gpr.key=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
```

> **Note**: You need a GitHub Personal Access Token with `read:packages` permission to download from GitHub Packages.

### Requirements
- Android API 21 (Android 5.0) or higher

## Usage

### Initialize in Application class

Set up once and all Activities and Fragments will be automatically tracked:

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ScreenNameViewer.getInstance().initialize(
            application = this,
            settings = ScreenNameViewerSetting(
                debugModeCondition = { BuildConfig.DEBUG },
                enabledCondition = {
                    PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean("debug_overlay_enabled", true)
                },
            ),
            config = ScreenNameOverlayConfig.default()
        )
    }
}
```

### Initialize in NavHost class (For Compose Integration)

```kotlin
    ScreenNameTracker(navController) {
        NavHost() { /*...*/ }
    }
```

## Configuration

### UI Customization

```kotlin
val config = ScreenNameOverlayConfig(
    textSize = 12f,                              // Text size
    textColor = Color.WHITE,                     // Text color
    backgroundColor = Color.argb(128, 0, 0, 0),  // Background color
    padding = 16,                                // Padding
    topMargin = 64,                              // Top margin
    activityGravity = Gravity.TOP or Gravity.START,  // Activity display position
    fragmentGravity = Gravity.TOP or Gravity.END,    // Fragment display position
    customLabelGravity = Gravity.TOP or Gravity.END  // Custom label display position
)

val lifecycleHandler = ScreenNameViewerLifecycleHandler(settings, config)
```
You can customize the style of the overlay that will be displayed on screen.

### Activation Condition Injection

```kotlin
val settings = ScreenNameViewerSetting(
    debugModeCondition = { BuildConfig.DEBUG },
    enabledCondition = { 
        PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("debug_overlay_enabled", true)
    }
)
```
- `debugModeCondition`: Injects debug mode condition.
- `enabledCondition`: Injects overlay feature activation condition.

## Contributors

<!-- readme: collaborators,contributors -start -->
<table>
	<tbody>
		<tr>
            <td align="center">
                <a href="https://github.com/dongx0915">
                    <img src="https://avatars.githubusercontent.com/u/63500239?v=4" width="100;" alt="dongx0915"/>
                    <br />
                    <sub><b>Donghyeon Kim</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="https://github.com/murjune">
                    <img src="https://avatars.githubusercontent.com/u/87055456?v=4" width="100;" alt="murjune"/>
                    <br />
                    <sub><b>JUNWON LEE</b></sub>
                </a>
            </td>
		</tr>
	<tbody>
</table>
<!-- readme: collaborators,contributors -end -->
