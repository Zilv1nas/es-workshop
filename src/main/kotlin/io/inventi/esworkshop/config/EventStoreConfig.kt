package io.inventi.esworkshop.config


import com.github.msemys.esjc.EventStoreBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.validation.annotation.Validated
import java.net.InetSocketAddress
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


@Configuration
class EventStoreConfig {
    @Bean
    @Primary
    fun eventStore(properties: EventStoreProperties) = EventStoreBuilder.newBuilder()
            .maxReconnections(-1)
            .singleNodeAddress(InetSocketAddress.createUnresolved(properties.host, properties.port))
            .userCredentials(properties.username, properties.password)
            .build()
            .apply { connect() }
}