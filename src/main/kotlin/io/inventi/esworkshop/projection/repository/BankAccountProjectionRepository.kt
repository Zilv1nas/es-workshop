package io.inventi.esworkshop.projection.repository

import io.inventi.esworkshop.projection.model.BankAccountProjection
import org.springframework.data.repository.CrudRepository

interface BankAccountProjectionRepository : CrudRepository<BankAccountProjection, String>