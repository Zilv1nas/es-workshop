package io.inventi.esworkshop.domain.event

import java.time.Instant

interface DomainEvent {
    val createdAt: Instant
}