package io.inventi.esworkshop.web.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(value = Deposit::class, name = "DEPOSIT"),
        JsonSubTypes.Type(value = OutgoingPayment::class, name = "OUTGOING_PAYMENT")
)
sealed class NewAccountTransaction

data class Deposit(
        val amount: BigDecimal
) : NewAccountTransaction()

data class OutgoingPayment(
        val amount: BigDecimal,
        val payeeAccountId: String
) : NewAccountTransaction()