import mealster.libraries

plugins{
    id("anas.kotlin-common")
    //id("org.springframework.boot) adding this here spring boot will recognize a module that should have a main function (entry-point) the moment we add this plugin.
    id("io.spring.dependency-management")
}

dependencies {

    "implementation"(libraries.findLibrary("kotlin-reflect").get())
    "implementation"(libraries.findLibrary("kotlin-stdlib").get())
    "implementation"(libraries.findLibrary("spring-boot-starter-web").get())

    "testImplementation"(libraries.findLibrary("spring-boot-starter-test").get())
    "testImplementation"(libraries.findLibrary("kotlin-test-junit5").get())
    "testImplementation"(libraries.findLibrary("junit-platform-launcher").get())
}