package io.inventi.esworkshop.domain.aggregate.bankaccount

import org.springframework.stereotype.Component
import java.time.Clock

@Component
class BankAccountFactory(private val clock: Clock) {
    fun create(id: String) = BankAccount(id, clock)
}