package io.inventi.esworkshop.projection.model

import io.inventi.esworkshop.domain.aggregate.bankaccount.Currency
import org.hibernate.annotations.Formula
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
data class BankAccountProjection(
        @Id
        val id: String,
        val name: String,
        @Enumerated(EnumType.STRING)
        val currency: Currency,
        @Formula("COALESCE((select sum(at.amount_delta) from account_transaction_projection at where at.account_id = id), 0)")
        val balance: BigDecimal = ZERO
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BankAccountProjection) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}