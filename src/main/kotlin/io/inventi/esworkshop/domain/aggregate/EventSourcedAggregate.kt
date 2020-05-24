package io.inventi.esworkshop.domain.aggregate

import com.fasterxml.jackson.databind.util.ClassUtil.getClassMethods
import io.inventi.esworkshop.domain.event.DomainEvent
import java.lang.reflect.Method

private const val EVENT_HANDLER_FUNCTION = "apply"

abstract class EventSourcedAggregate<EVENT : DomainEvent> : Aggregate {
    val changes = mutableListOf<EVENT>()
    internal var version: Long? = null

    fun apply(event: EVENT) {
        val eventType = event::class.java
        findMatchingMethods(eventType).forEach { it ->
            it.isAccessible = true
            it(this, event)
        }
    }

    fun causes(event: EVENT) {
        changes.add(event)
        apply(event)
    }

    private fun findMatchingMethods(eventType: Class<out EVENT>): List<Method> {
        return getClassMethods(this::class.java)
                .asSequence()
                .filter { it.name == EVENT_HANDLER_FUNCTION }
                .filter { it.parameterCount > 0 }
                .filter { it.parameterTypes.first().isAssignableFrom(eventType) }
                .toList()
    }
}