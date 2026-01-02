pluginManagement {
    includeBuild("build-logic")
    repositories{
        gradlePluginPortal()
    }
}

rootProject.name = "Mealster"

include("app")
include("user")
include("common")
include("notification")
include("meal")