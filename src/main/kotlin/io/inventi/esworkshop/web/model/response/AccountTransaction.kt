package io.inventi.esworkshop.web.model.response

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
sealed class AccountTransaction {
    abstract val id: String
    abstract val accountId: String
    abstract val status: Status

    enum class Status { PENDING, COMPLETED, REJECTED }
}

data class Deposit(
        override val id: String,
        override val accountId: String,
        override val status: Status = Status.PENDING,
        val amount: BigDecimal
) : AccountTransaction()

data class Transfer(
        override val id: String,
        override val accountId: String,
        override val status: Status = Status.PENDING,
        val amount: BigDecimal,
        val payeeAccountId: String
) : AccountTransaction()