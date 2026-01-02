plugins {
    id("anas.spring-boot-app")

}

group = "com.anas"
version = "0.0.1-SNAPSHOT"
description = "Spring Boot backend for Recipe app"


dependencies {
    implementation(projects.common)
    implementation(projects.meal)
    implementation(projects.notification)
    implementation(projects.user)
}

