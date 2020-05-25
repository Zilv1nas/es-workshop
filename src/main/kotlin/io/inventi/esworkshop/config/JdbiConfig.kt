package io.inventi.esworkshop.config

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import java.sql.Types
import java.util.UUID
import javax.sql.DataSource


@Configuration
class JdbiConfig {

    @Bean
    fun jdbi(dataSource: DataSource): Jdbi {
        val dataSourceProxy = TransactionAwareDataSourceProxy(dataSource)
        return Jdbi.create(dataSourceProxy).apply {
            installPlugins()
            registerArgument(UUIDArgumentFactory())
        }
    }
}

private class UUIDArgumentFactory : AbstractArgumentFactory<UUID>(Types.OTHER) {
    override fun build(value: UUID, config: ConfigRegistry): Argument {
        return Argument { position, statement, _ ->
            statement.setObject(position, value.toString(), Types.OTHER)
        }
    }
}
