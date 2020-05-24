package io.inventi.esworkshop.domain.aggregate.bankaccount

import io.inventi.esworkshop.domain.aggregate.EventSourcedAggregate
import io.inventi.esworkshop.domain.event.bankaccount.BankAccountCreated
import io.inventi.esworkshop.domain.event.bankaccount.BankAccountEvent
import java.time.Clock
import java.time.Instant

class BankAccount(
        override val id: String,
        private val clock: Clock
) : EventSourcedAggregate<BankAccountEvent>() {
    private lateinit var name: String
    private lateinit var currency: Currency
    private var balance = 0

    private fun now() = Instant.now(clock)

    fun register(name: String, currency: Currency) {
        causes(BankAccountCreated(now(), name, currency))
    }

    private fun apply(accountCreated: BankAccountCreated) {
        name = accountCreated.name
        currency = accountCreated.currency
    }
}