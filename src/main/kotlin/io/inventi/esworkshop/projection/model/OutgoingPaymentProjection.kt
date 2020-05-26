package io.inventi.esworkshop.projection.model

import java.math.BigDecimal
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value = "OUTGOING_PAYMENT")
class OutgoingPaymentProjection(
        id: String,
        accountId: String,
        status: Status,
        amountDelta: BigDecimal,
        val payeeAccountId: String
) : AccountTransactionProjection(id, accountId, status, amountDelta) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OutgoingPaymentProjection) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}