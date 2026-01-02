plugins {
    id("java-library")
    id("anas.spring-boot-service")
    kotlin("plugin.jpa")
}

group = "com.anas"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}