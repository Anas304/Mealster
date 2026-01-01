plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.kotlin.jpa) apply false
}
group =
    "com.anas" // group is basically a namespace/organization name like org.springframework.boot means SpringBoot team
version =
    "0.0.1-SNAPSHOT" // version here means the version this build produces, like if its stable or in testing phase i.e., SNAPSHOT
// in short, group and version are properties of a Gradle project

//subproject ensures “Every subproject shares the same identity base.”
//Without this ,You’d need
// common/build.gradle.kts
//group = "com.anas"
//version = "0.0.1-SNAPSHOT"
// meal/build.gradle.kts
//group = "com.anas"
//version = "0.0.1-SNAPSHOT"
// notification/build.gradle.kts
//group = "com.anas"
//version = "0.0.1-SNAPSHOT"
subprojects {
    group = rootProject.group
    version = rootProject.version
}