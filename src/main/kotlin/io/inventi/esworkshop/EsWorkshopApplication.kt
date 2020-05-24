package io.inventi.esworkshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EsWorkshopApplication

fun main(args: Array<String>) {
	runApplication<EsWorkshopApplication>(*args)
}
