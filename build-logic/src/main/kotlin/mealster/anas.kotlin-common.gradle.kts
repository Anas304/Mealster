import mealster.libraries
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}
repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${libraries.findVersion("spring-boot").get()}")
    }
}

configure<KotlinJvmProjectExtension> {
    jvmToolchain(21)
}
//Below, you are telling Gradle
// "Hey, whenever you compile Kotlin: make it run on Java 21, be strict about nulls when talking to Java,
// and make sure annotations stick to properties so my frameworks (like Spring) don't get confused."
tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")

    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}