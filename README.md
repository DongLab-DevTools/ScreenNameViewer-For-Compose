# ScreenNameViewer-For-Compose

**[한국어 README](./README_ko.md)**

## Overview

![sample](https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose/blob/eae99cecc086002a6958e12620ec80647c89822f/.github/docs/images/screennameviewer-compose-exmaple.png)

<a href="https://github.com/DongLab-DevTools/ScreenNameViewer">
	<img src="https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose/blob/9c53027addec40826ed428567965cfc46d17149f/.github/docs/images/screen_name_viewer_link_thumb_xml_en.png"/>
</a>

<br>
<br>

ScreenNameViewer is a debugging tool that overlays the class name of the currently displayed screen.  
It allows you to intuitively check which screen is active, and in a Compose environment, it can also display the screen route.

This allows you to quickly find and navigate to the code for the desired screen, improving both debugging and development efficiency.

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
                username = props.getProperty("github_username")
                password = props.getProperty("github_token")
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
github_username=YOUR_GITHUB_USERNAME
github_token=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
```

> [!NOTE]
> You need a GitHub Personal Access Token with `read:packages` permission to download from GitHub Packages.

### Requirements
- Android API 21 (Android 5.0) or higher

## Usage

### Initialize in Application class

Set up once and all Activities and Fragments will be automatically tracked:

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer(this) {
            settings {
                debugMode { BuildConfig.DEBUG }
                enabled {
                    PreferenceManager.getDefaultSharedPreferences(this@MyApplication)
                        .getBoolean("debug_overlay_enabled", true)
                }
            }
            config {
                textStyle {
                    size = 12f
                    color = Color.WHITE
                }
                background {
                    color = Color.argb(128, 0, 0, 0)
                    padding = 16
                }
                position {
                    topMargin = 64
                    activity = Gravity.TOP or Gravity.START
                    fragment = Gravity.TOP or Gravity.END
                    composeRoute = Gravity.TOP or Gravity.END
                }
            }
        }
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

### DSL Configuration

You can configure the library using a simple DSL (Domain Specific Language):

```kotlin
initScreenNameViewer(this) {
    settings {
        debugMode { BuildConfig.DEBUG }
        enabled {
            PreferenceManager.getDefaultSharedPreferences(this@MyApplication)
                .getBoolean("debug_overlay_enabled", true)
        }
    }
    config {
        textStyle {
            size = 12f                    // Text size
            color = Color.WHITE           // Text color
        }
        background {
            color = Color.argb(128, 0, 0, 0)  // Background color
            padding = 16                      // Padding
        }
        position {
            topMargin = 64                                    // Top margin
            activity = Gravity.TOP or Gravity.START          // Activity display position
            fragment = Gravity.TOP or Gravity.END            // Fragment display position
            composeRoute = Gravity.TOP or Gravity.END        // Compose Route display position
        }
    }
}
```

### Configuration Options

- **settings**: Configure activation conditions
  - `debugMode`: Debug mode condition
  - `enabled`: Overlay feature activation condition

- **config**: Customize overlay appearance
  - `textStyle`: Text size and color
  - `background`: Background color and padding
  - `position`: Margin and display positions for different components

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
