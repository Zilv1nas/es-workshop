package io.inventi.esworkshop.domain.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.msemys.esjc.EventData
import com.github.msemys.esjc.EventStore
import com.github.msemys.esjc.ResolvedEvent
import com.github.msemys.esjc.operation.StreamNotFoundException
import io.inventi.esworkshop.domain.aggregate.EventSourcedAggregate
import io.inventi.esworkshop.domain.event.DomainEvent
import kotlin.reflect.KClass

private const val STREAM_START_EVENT_NO = 0L
private const val EVENT_LOAD_BATCH_SIZE = 4096

abstract class EventSourcedAggregateRepository<AGGREGATE, EVENT>(
        protected val eventStore: EventStore,
        protected val objectMapper: ObjectMapper,
        protected val eventPackage: String,
        protected val initAggregate: (id: String) -> AGGREGATE
) : AggregateRepository<AGGREGATE> where EVENT : DomainEvent, AGGREGATE : EventSourcedAggregate<EVENT> {

    override fun find(id: String): AGGREGATE? {
        return try {
            val aggregate = initAggregate(id)
            val stream = id.toStreamName(aggregate::class)
            val firstEventNumber = findFirstEventNumber(stream)

            eventStore.iterateStreamEventsForward(stream, firstEventNumber, EVENT_LOAD_BATCH_SIZE, false)
                    .forEach {
                        aggregate.apply(it.toDomainEvent())
                        aggregate.version = it.event.eventNumber
                    }

            aggregate
        } catch (e: StreamNotFoundException) {
            null
        }
    }

    override fun save(aggregate: AGGREGATE) {
        val stream = aggregate.id.toStreamName(aggregate::class)
        val eventsToAppend = aggregate.changes.map {
            EventData.newBuilder()
                    .jsonData(objectMapper.writeValueAsBytes(it))
                    .type(it::class.simpleName)
                    .build()
        }

        eventStore.appendToStream(stream, aggregate.version, eventsToAppend).join()
    }

    @Suppress("UNCHECKED_CAST")
    private fun ResolvedEvent.toDomainEvent(): EVENT {
        val eventData = this.event.data
        val eventClass = Class.forName("$eventPackage.${this.event.eventType}")
        return objectMapper.readValue(eventData, eventClass) as EVENT
    }

    private fun String.toStreamName(aggregateType: KClass<out AGGREGATE>) = aggregateType.simpleName + "-" + this

    private fun findFirstEventNumber(stream: String): Long = eventStore.getStreamMetadata(stream)
            .thenApply { it.streamMetadata.truncateBefore }
            .get()
            ?: STREAM_START_EVENT_NO
}