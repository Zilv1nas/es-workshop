package io.inventi.esworkshop.projection.model

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

data class AccountTransactionProjectionId(
        val id: String? = null,
        val accountId: String? = null
) : Serializable

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
@Table(name = "account_transaction_projection")
@IdClass(AccountTransactionProjectionId::class)
abstract class AccountTransactionProjection(
        @Id
        val id: String,
        @Id
        val accountId: String,
        @Enumerated(EnumType.STRING)
        var status: Status,
        val amountDelta: BigDecimal
) {
    enum class Status { PENDING, COMPLETED, REJECTED }
}