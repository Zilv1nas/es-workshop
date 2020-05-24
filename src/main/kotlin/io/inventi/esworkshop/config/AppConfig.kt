package io.inventi.esworkshop.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.inventi.esworkshop.service.impl.UuidGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Clock


@Configuration
class AppConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = ObjectMapper().apply {
        disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        registerModule(KotlinModule(nullIsSameAsDefault = true))
        registerModule(JavaTimeModule())
    }

    @Bean
    fun idGenerator() = UuidGenerator()

    @Bean
    fun clock() = Clock.systemDefaultZone()
}
