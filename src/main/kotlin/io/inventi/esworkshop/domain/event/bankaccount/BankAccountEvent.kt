package io.inventi.esworkshop.domain.event.bankaccount

import io.inventi.esworkshop.domain.event.DomainEvent

interface BankAccountEvent : DomainEvent {
    val accountId: String
}