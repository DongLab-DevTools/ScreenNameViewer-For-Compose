import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.vanniktech.maven.publish)
}

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
}

mavenPublishing {
    coordinates(
        groupId = "io.github.dongx0915",
        artifactId = "screennameviewer-compose",
        version = libs.versions.sdk.version.get()
    )

    pom {
        name.set("ScreenNameViewer for Compose")
        description.set("A debug library that displays the current Activity/Fragment and Compose screen route as an on-screen overlay.")
        url.set("https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose")
        inceptionYear.set("2025")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("dongx0915")
                name.set("Donghyeon Kim")
                email.set("donghyeon0915@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose")
            connection.set("scm:git:git://github.com/DongLab-DevTools/ScreenNameViewer-For-Compose.git")
            developerConnection.set("scm:git:ssh://git@github.com/DongLab-DevTools/ScreenNameViewer-For-Compose.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}