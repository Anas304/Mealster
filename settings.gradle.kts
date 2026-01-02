pluginManagement {
    includeBuild("build-logic")
    repositories{
        gradlePluginPortal()
    }
}
//This will allow access to all the library module( user, meal,common and notification) in the app module i.e., Root module
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Mealster"

include("app")
include("user")
include("common")
include("notification")
include("meal")