package io.inventi.esworkshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(io.inventi.eventstore.EventStoreInitConfig::class)
class EsWorkshopApplication

fun main(args: Array<String>) {
    runApplication<EsWorkshopApplication>(*args)
}
