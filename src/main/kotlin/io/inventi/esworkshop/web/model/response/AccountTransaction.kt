package io.inventi.esworkshop.web.model.response

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.inventi.esworkshop.projection.model.AccountTransactionProjection.Status
import io.inventi.esworkshop.web.model.DEPOSIT
import io.inventi.esworkshop.web.model.INCOMING_PAYMENT
import io.inventi.esworkshop.web.model.OUTGOING_PAYMENT
import java.math.BigDecimal

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(value = Deposit::class, name = DEPOSIT),
        JsonSubTypes.Type(value = OutgoingPayment::class, name = OUTGOING_PAYMENT),
        JsonSubTypes.Type(value = IncomingPayment::class, name = INCOMING_PAYMENT)
)
sealed class AccountTransaction {
    abstract val id: String
    abstract val accountId: String
    abstract val status: Status
}

data class Deposit(
        override val id: String,
        override val accountId: String,
        override val status: Status = Status.PENDING,
        val amount: BigDecimal
) : AccountTransaction()

data class OutgoingPayment(
        override val id: String,
        override val accountId: String,
        override val status: Status = Status.PENDING,
        val amount: BigDecimal,
        val payeeAccountId: String
) : AccountTransaction()

data class IncomingPayment(
        override val id: String,
        override val accountId: String,
        override val status: Status = Status.PENDING,
        val amount: BigDecimal,
        val payeeAccountId: String
) : AccountTransaction()