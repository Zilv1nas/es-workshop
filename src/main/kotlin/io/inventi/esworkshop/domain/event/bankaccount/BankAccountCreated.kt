package io.inventi.esworkshop.domain.event.bankaccount

import io.inventi.esworkshop.domain.aggregate.bankaccount.Currency
import java.time.Instant

data class BankAccountCreated(
        override val createdAt: Instant,
        override val accountId: String,
        val name: String,
        val currency: Currency
) : BankAccountEvent