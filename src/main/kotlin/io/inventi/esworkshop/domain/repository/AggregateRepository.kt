package io.inventi.esworkshop.domain.repository

import io.inventi.esworkshop.domain.aggregate.Aggregate

interface AggregateRepository<AGGREGATE : Aggregate> {
    fun find(id: String): AGGREGATE?
    fun save(aggregate: AGGREGATE)
}