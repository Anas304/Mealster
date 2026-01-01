package com.anas.mealster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MealsterApplication

fun main(args: Array<String>) {
	runApplication<MealsterApplication>(*args)
}
