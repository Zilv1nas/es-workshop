package io.inventi.esworkshop.web.model.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.inventi.esworkshop.web.model.DEPOSIT
import io.inventi.esworkshop.web.model.TRANSFER
import java.math.BigDecimal

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(value = Deposit::class, name = DEPOSIT),
        JsonSubTypes.Type(value = Transfer::class, name = TRANSFER)
)
sealed class NewAccountTransaction {
    abstract val accountId: String
}

data class Deposit(
        override val accountId: String,
        val amount: BigDecimal
) : NewAccountTransaction()

data class Transfer(
        override val accountId: String,
        val amount: BigDecimal,
        val payeeAccountId: String
) : NewAccountTransaction()