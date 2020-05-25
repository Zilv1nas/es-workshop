package io.inventi.esworkshop.domain.event.bankaccount

import java.time.Instant

data class OutgoingPaymentCompleted(
        override val createdAt: Instant,
        override val accountId: String,
        val transactionId: String
) : BankAccountEvent