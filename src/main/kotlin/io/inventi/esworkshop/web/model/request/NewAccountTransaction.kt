package io.inventi.esworkshop.web.model.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.inventi.esworkshop.web.model.DEPOSIT
import io.inventi.esworkshop.web.model.PAYMENT
import java.math.BigDecimal

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        JsonSubTypes.Type(value = Deposit::class, name = DEPOSIT),
        JsonSubTypes.Type(value = Payment::class, name = PAYMENT)
)
sealed class NewAccountTransaction

data class Deposit(
        val amount: BigDecimal
) : NewAccountTransaction()

data class Payment(
        val amount: BigDecimal,
        val payeeAccountId: String
) : NewAccountTransaction()