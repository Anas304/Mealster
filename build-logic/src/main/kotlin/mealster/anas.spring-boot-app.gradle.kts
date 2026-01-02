plugins{
    id("anas.spring-boot-service")
    id("org.springframework.boot") // and put it here instead in app build.gradle: more info in anas.spring-boot-service.gradle.kts
    kotlin("plugin.spring")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
