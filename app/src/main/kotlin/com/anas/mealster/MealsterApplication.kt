package com.anas.mealster

import Test
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MealsterApplication

fun main(args: Array<String>) {
	Test()
	runApplication<MealsterApplication>(*args)
}
