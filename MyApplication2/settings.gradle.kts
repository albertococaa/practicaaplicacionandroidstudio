pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "7.4.1"
        id("org.jetbrains.kotlin.android") version "1.8.21"
        id("org.jetbrains.kotlin.kapt") version "1.8.21"

        // Aquí añadimos Safe-Args
        id("androidx.navigation.safeargs.kotlin") version "2.8.9"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplication"
include(":app")
