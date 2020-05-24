package io.inventi.esworkshop.domain.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.msemys.esjc.EventStore
import io.inventi.esworkshop.domain.aggregate.bankaccount.BankAccount
import io.inventi.esworkshop.domain.aggregate.bankaccount.BankAccountFactory
import io.inventi.esworkshop.domain.event.bankaccount.BankAccountEvent
import org.springframework.stereotype.Repository

@Repository
class BankAccountRepository(
        eventStore: EventStore,
        objectMapper: ObjectMapper,
        bankAccountFactory: BankAccountFactory
) : EventSourcedAggregateRepository<BankAccount, BankAccountEvent>(
        eventStore,
        objectMapper,
        "io.inventi.esworkshop.domain.event.bankaccount",
        bankAccountFactory::create
)