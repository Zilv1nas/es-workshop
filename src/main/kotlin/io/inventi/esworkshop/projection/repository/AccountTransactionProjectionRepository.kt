package io.inventi.esworkshop.projection.repository

import io.inventi.esworkshop.projection.model.AccountTransactionProjection
import io.inventi.esworkshop.projection.model.AccountTransactionProjectionId
import org.springframework.data.repository.CrudRepository

interface AccountTransactionProjectionRepository : CrudRepository<AccountTransactionProjection, AccountTransactionProjectionId> {
    fun findAllByAccountId(accountId: String): List<AccountTransactionProjection>
}