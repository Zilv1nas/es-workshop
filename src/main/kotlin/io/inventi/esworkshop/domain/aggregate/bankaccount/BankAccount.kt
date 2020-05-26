package io.inventi.esworkshop.domain.aggregate.bankaccount

import io.inventi.esworkshop.domain.aggregate.EventSourcedAggregate
import io.inventi.esworkshop.domain.aggregate.bankaccount.AccountTransaction.Status.COMPLETED
import io.inventi.esworkshop.domain.aggregate.bankaccount.AccountTransaction.Status.PENDING
import io.inventi.esworkshop.domain.event.bankaccount.BankAccountCreated
import io.inventi.esworkshop.domain.event.bankaccount.BankAccountEvent
import io.inventi.esworkshop.domain.event.bankaccount.IncomingPaymentReceived
import io.inventi.esworkshop.domain.event.bankaccount.IncomingPaymentRejected
import io.inventi.esworkshop.domain.event.bankaccount.IncomingPaymentRejected.RejectionReason.ACCOUNT_LIMIT_EXCEEDED
import io.inventi.esworkshop.domain.event.bankaccount.MoneyDeposited
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentCompleted
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentInitiated
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentRejected
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant

class BankAccount(
        override val id: String,
        private val clock: Clock
) : EventSourcedAggregate<BankAccountEvent>() {
    private val accountLimit = 10000.toBigDecimal()
    private lateinit var name: String
    private lateinit var currency: Currency
    private val transactionsById = mutableMapOf<String, AccountTransaction>()

    private val balance: BigDecimal
        get() = transactionsById.values
                .filter { !it.rejected }
                .map { it.amountDelta }
                .fold(BigDecimal.ZERO, BigDecimal::add)

    private fun now() = Instant.now(clock)

    fun register(name: String, currency: Currency) {
        if (this::name.isInitialized) throw IllegalStateException("Bank account $id is already registered")

        causes(BankAccountCreated(now(), id, name, currency))
    }

    fun depositMoney(transactionId: String, amount: BigDecimal) {
        if (transactionsById.containsKey(transactionId)) return

        causes(MoneyDeposited(now(), id, transactionId, amount))
    }

    fun initiateOutgoingPayment(transactionId: String, amount: BigDecimal, payeeAccountId: String) {
        if (transactionsById.containsKey(transactionId)) return

        causes(OutgoingPaymentInitiated(now(), id, transactionId, amount, payeeAccountId))
    }

    fun receiveIncomingPayment(transactionId: String, amount: BigDecimal, payerAccountId: String) {
        if (transactionsById.containsKey(transactionId)) return

        if (balance + amount > accountLimit) {
            causes(IncomingPaymentRejected(now(), id, transactionId, amount, payerAccountId, ACCOUNT_LIMIT_EXCEEDED))
        } else {
            causes(IncomingPaymentReceived(now(), id, transactionId, amount, payerAccountId))
        }
    }

    fun rejectOutgoingPayment(transactionId: String) {
        if (transactionsById[transactionId]?.rejected == true) return

        causes(OutgoingPaymentRejected(now(), id, transactionId))
    }

    fun completeOutgoingPayment(transactionId: String) {
        if (transactionsById[transactionId]?.completed == true) return

        causes(OutgoingPaymentCompleted(now(), id, transactionId))
    }

    private fun apply(accountCreated: BankAccountCreated) {
        name = accountCreated.name
        currency = accountCreated.currency
    }

    private fun apply(moneyDeposited: MoneyDeposited) {
        with(moneyDeposited) {
            transactionsById[transactionId] = Deposit(transactionId, COMPLETED, amount)
        }
    }

    private fun apply(outgoingPaymentInitiated: OutgoingPaymentInitiated) {
        with(outgoingPaymentInitiated) {
            transactionsById[transactionId] = OutgoingPayment(transactionId, PENDING, -amount, payeeAccountId)
        }
    }

    private fun apply(incomingPaymentReceived: IncomingPaymentReceived) {
        with(incomingPaymentReceived) {
            transactionsById[transactionId] = IncomingPayment(transactionId, COMPLETED, amount, payerAccountId)
        }
    }

    private fun apply(outgoingPaymentRejected: OutgoingPaymentRejected) {
        with(outgoingPaymentRejected) {
            transactionsById[transactionId]!!.reject()
        }
    }

    private fun apply(outgoingPaymentCompleted: OutgoingPaymentCompleted) {
        with(outgoingPaymentCompleted) {
            transactionsById[transactionId]!!.complete()
        }
    }
}