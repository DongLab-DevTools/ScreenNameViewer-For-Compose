# ScreenNameViewer-For-Compose
[![Hits](https://myhits.vercel.app/api/hit/https%3A%2F%2Fgithub.com%2FDongLab-DevTools%2FScreenNameViewer-For-Compose%3Ftab%3Dreadme-ov-file?color=blue&label=hits&size=small)](https://myhits.vercel.app)
[![Platform](https://img.shields.io/badge/platform-Android-3DDC84?style=flat-square&logo=android)](https://developer.android.com)
[![Min SDK](https://img.shields.io/badge/min%20sdk-21-green?style=flat-square)](https://developer.android.com)
[![Jitpack](https://jitpack.io/v/DongLab-DevTools/ScreenNameViewer-For-Compose.svg)](https://jitpack.io/#DongLab-DevTools/ScreenNameViewer-For-Compose)


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

<br>

## Features

- **Real-time class name display**: Shows Activity and Fragment class names on screen in real-time
- **Automatic lifecycle management**: Automatically tracks all Activities and Fragments at the Application level
- **Debug-only**: Automatically disabled in release builds for safety
- **UI customization**: Freely configure text size, color, position, etc.
- **Memory safe**: Prevents memory leaks using WeakReference
- **Touch interaction**: Touch overlay to show full class name in toast

<br>

## Installation

### Step 1: Add GitHub Packages repository

Add the GitHub Packages repository to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
		maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add dependency

Add the library to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation 'com.github.DongLab-DevTools:ScreenNameViewer-For-Compose:latestVersion'
}
```

<br>

### Requirements
- Android API 21 (Android 5.0) or higher

<br>

## Usage

### Initialize in Application class

Set up once and all Activities and Fragments will be automatically tracked:

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer(this) {
            settings {
                debugModeCondition = BuildConfig.DEBUG
                enableCondition = PreferenceManager.getDefaultSharedPreferences(this@MyApplication)
                        .getBoolean("debug_overlay_enabled", true)
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

<br>

## Configuration

### DSL Configuration

You can configure the library using a simple DSL (Domain Specific Language):

```kotlin
initScreenNameViewer(this) {
    settings {
        debugModeCondition = BuildConfig.DEBUG
        enableCondition = PreferenceManager
            .getDefaultSharedPreferences(this@MyApplication)
            .getBoolean("debug_overlay_enabled", true)
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

<br>

### Configuration Options

- **settings**: Configure activation conditions
  - `debugModeCondition`: Debug mode condition
  - `enableCondition`: Overlay feature activation condition

- **config**: Customize overlay appearance
  - `textStyle`: Text size and color
  - `background`: Background color and padding
  - `position`: Margin and display positions for different components

<br>

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
