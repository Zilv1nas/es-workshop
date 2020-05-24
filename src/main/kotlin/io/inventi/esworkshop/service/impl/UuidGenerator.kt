package io.inventi.esworkshop.service.impl

import io.inventi.esworkshop.service.IdGenerator
import java.util.UUID

class UuidGenerator : IdGenerator {
    override fun generate() = UUID.randomUUID().toString()
}