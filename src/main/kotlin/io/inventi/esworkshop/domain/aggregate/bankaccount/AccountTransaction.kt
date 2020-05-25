package io.inventi.esworkshop.domain.aggregate.bankaccount

import java.math.BigDecimal

sealed class AccountTransaction {
    enum class Status { PENDING, COMPLETED, REJECTED }

    abstract val transactionId: String
    abstract var status: Status
    abstract val amountDelta: BigDecimal

    val rejected get() = status == Status.REJECTED
    val completed get() = status == Status.COMPLETED

    fun complete() {
        status = Status.COMPLETED
    }

    fun reject() {
        status = Status.REJECTED
    }
}

data class Deposit(
        override val transactionId: String,
        override var status: Status,
        override val amountDelta: BigDecimal
) : AccountTransaction()

data class OutgoingPayment(
        override val transactionId: String,
        override var status: Status,
        override val amountDelta: BigDecimal,
        val payeeAccountId: String
) : AccountTransaction()

data class IncomingPayment(
        override val transactionId: String,
        override var status: Status,
        override val amountDelta: BigDecimal,
        val payerAccountId: String
) : AccountTransaction()