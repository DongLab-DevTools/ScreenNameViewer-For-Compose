import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("maven-publish")
}

group = "com.donglab.devtools"
version = libs.versions.sdk.version.get()

android {
    namespace = "com.donglab.screennameviewer.compose.lib"
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
    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    debugImplementation(libs.leakcanary.android)
}

publishing {
    repositories {
        maven {
            val props = Properties().apply {
                load(rootProject.file("github.properties").inputStream())
            }
            url = uri(props.getProperty("url"))

            credentials {
                username = props.getProperty("github_username")
                password = props.getProperty("github_token")
            }
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "screennameviewer-compose"

                // POM Metadata (Optional)
                pom {
                    name.set("ScreenNameViewer for Compose")
                    description.set("Screen name viewer library for Jetpack Compose")
                    url.set("https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose")
                }
            }
        }
    }
}