package io.inventi.esworkshop.domain.event.bankaccount

import java.math.BigDecimal
import java.time.Instant

data class IncomingPaymentRejected(
        override val createdAt: Instant,
        override val accountId: String,
        val transactionId: String,
        val amount: BigDecimal,
        val payerAccountId: String,
        val reason: RejectionReason
) : BankAccountEvent {
    enum class RejectionReason { ACCOUNT_LIMIT_EXCEEDED }
}
