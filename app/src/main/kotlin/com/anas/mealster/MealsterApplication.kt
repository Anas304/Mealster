package com.anas.mealster

import Test
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class MealsterApplication

fun main(args: Array<String>) {
	println("SPRING BOOT APP STARTING")
	println("SPRING_DATASOURCE_USERNAME env = ${System.getenv("SPRING_DATASOURCE_USERNAME")}")
	println("POSTGRES_PASSWORD exists = ${System.getenv("POSTGRES_PASSWORD") != null}")
	runApplication<MealsterApplication>(*args)
}
@Component
class DatasourceDebug(
	@Value("\${spring.datasource.url}") private val url: String,
	@Value("\${spring.datasource.username}") private val username: String,
	@Value("\${spring.profiles.active:default}") private val activeProfile: String
) {
	@PostConstruct
	fun debug() {
		println("Bound datasource username = $username")
		println("Bound datasource url = $url")
		println("Active profile = $activeProfile")
	}
}