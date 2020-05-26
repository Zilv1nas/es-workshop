package io.inventi.esworkshop.projection

import io.inventi.esworkshop.domain.event.bankaccount.BankAccountCreated
import io.inventi.esworkshop.domain.event.bankaccount.IncomingPaymentReceived
import io.inventi.esworkshop.domain.event.bankaccount.MoneyDeposited
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentCompleted
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentInitiated
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentRejected
import io.inventi.esworkshop.projection.model.AccountTransactionProjection.Status.COMPLETED
import io.inventi.esworkshop.projection.model.AccountTransactionProjection.Status.PENDING
import io.inventi.esworkshop.projection.model.AccountTransactionProjection.Status.REJECTED
import io.inventi.esworkshop.projection.model.AccountTransactionProjectionId
import io.inventi.esworkshop.projection.model.BankAccountProjection
import io.inventi.esworkshop.projection.model.Currency
import io.inventi.esworkshop.projection.model.DepositProjection
import io.inventi.esworkshop.projection.model.IncomingPaymentProjection
import io.inventi.esworkshop.projection.model.OutgoingPaymentProjection
import io.inventi.esworkshop.projection.repository.AccountTransactionProjectionRepository
import io.inventi.esworkshop.projection.repository.BankAccountProjectionRepository
import io.inventi.eventstore.eventhandler.IdempotentEventHandler
import io.inventi.eventstore.eventhandler.annotation.EventHandler
import io.inventi.eventstore.util.LoggerDelegate
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BankAccountProjector(
        private val bankAccountProjectionRepository: BankAccountProjectionRepository,
        private val accountTransactionProjectionRepository: AccountTransactionProjectionRepository
) : IdempotentEventHandler(
        "\$ce-BankAccount",
        "bank-account-projector"
) {
    companion object {
        private val logger: Logger by LoggerDelegate()
    }

    @EventHandler
    fun onBankAccountCreated(event: BankAccountCreated) {
        bankAccountProjectionRepository.save(BankAccountProjection(
                event.accountId,
                event.name,
                Currency.valueOf(event.currency.name)
        ))
    }


    @EventHandler
    fun onMoneyDeposited(event: MoneyDeposited) {
        accountTransactionProjectionRepository.save(DepositProjection(
                event.transactionId,
                event.accountId,
                COMPLETED,
                event.amount
        ))
    }

    @EventHandler
    fun onIncomingPaymentReceived(event: IncomingPaymentReceived) {
        accountTransactionProjectionRepository.save(IncomingPaymentProjection(
                event.transactionId,
                event.accountId,
                COMPLETED,
                event.amount,
                event.payerAccountId
        ))
    }


    @EventHandler
    fun onOutgoingPaymentInitiated(event: OutgoingPaymentInitiated) {
        accountTransactionProjectionRepository.save(OutgoingPaymentProjection(
                event.transactionId,
                event.accountId,
                PENDING,
                -event.amount,
                event.payeeAccountId
        ))
    }

    @EventHandler
    fun onOutgoingPaymentCompleted(event: OutgoingPaymentCompleted) {
        val transaction = accountTransactionProjectionRepository
                .findByIdOrNull(AccountTransactionProjectionId(event.transactionId, event.accountId))
                ?: throw IllegalStateException("Could not find transaction ${event.transactionId} while handling event: $event")

        transaction.status = COMPLETED
    }

    @EventHandler
    fun onOutgoingPaymentRejected(event: OutgoingPaymentRejected) {
        val transaction = accountTransactionProjectionRepository
                .findByIdOrNull(AccountTransactionProjectionId(event.transactionId, event.accountId))
                ?: throw IllegalStateException("Could not find transaction ${event.transactionId} while handling event: $event")

        transaction.status = REJECTED
    }
}