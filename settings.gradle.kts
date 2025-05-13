pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // ✅ Make sure this is included
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BOOKMARK"
include(":app")
