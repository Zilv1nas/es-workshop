package io.inventi.esworkshop.domain.event.bankaccount

import java.time.Instant

data class OutgoingPaymentRejected(
        override val createdAt: Instant,
        override val accountId: String,
        val transactionId: String
) : BankAccountEvent