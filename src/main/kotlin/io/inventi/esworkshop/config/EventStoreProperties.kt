package io.inventi.esworkshop.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@ConfigurationProperties("eventstore")
@Configuration
@Validated
class EventStoreProperties {

    @field:NotEmpty
    lateinit var host: String

    @field:Min(1)
    @field:Max(65535)
    var port: Int = -1

    @field:NotEmpty
    lateinit var username: String

    @field:NotEmpty
    lateinit var password: String
}