import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("maven-publish")
}

android {
    namespace = "com.donglab.screennameviewer.compose.lib.noop"
    compileSdk = 36

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    // Minimal Compose dependencies for API compatibility
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.donglab.devtools"
                artifactId = "screennameviewer-compose-noop"
                version = libs.versions.sdk.version.get()

                // POM Metadata (Optional)
                pom {
                    name.set("ScreenNameViewer for Compose (No-op)")
                    description.set("No-operation version of ScreenNameViewer library for Jetpack Compose (for release builds)")
                    url.set("https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose")
                }
            }
        }
    }
}
