package io.inventi.esworkshop.projection.model

import java.math.BigDecimal
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value = "INCOMING_PAYMENT")
class IncomingPaymentProjection(
        id: String,
        accountId: String,
        status: Status,
        amountDelta: BigDecimal,
        val payerAccountId: String
) : AccountTransactionProjection(id, accountId, status, amountDelta) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IncomingPaymentProjection) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}