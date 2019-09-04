package com.example.demos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemosApplication

fun main(args: Array<String>) {
	runApplication<DemosApplication>(*args)
}
