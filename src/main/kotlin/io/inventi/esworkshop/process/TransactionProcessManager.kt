package io.inventi.esworkshop.process

import io.inventi.esworkshop.domain.event.bankaccount.IncomingPaymentReceived
import io.inventi.esworkshop.domain.event.bankaccount.IncomingPaymentRejected
import io.inventi.esworkshop.domain.event.bankaccount.OutgoingPaymentInitiated
import io.inventi.esworkshop.domain.repository.BankAccountRepository
import io.inventi.eventstore.eventhandler.IdempotentEventHandler
import io.inventi.eventstore.eventhandler.annotation.EventHandler
import org.springframework.stereotype.Component

@Component
class TransactionProcessManager(
        private val bankAccountRepository: BankAccountRepository
) : IdempotentEventHandler(
        "\$ce-BankAccount",
        "transaction-process-manager"
) {
    @EventHandler
    fun onPaymentInitiated(event: OutgoingPaymentInitiated) {
        val payeeAccount = findBankAccount(event.payeeAccountId)
        payeeAccount.receiveIncomingPayment(event.transactionId, event.amount, event.accountId)
        bankAccountRepository.save(payeeAccount)
    }

    @EventHandler
    fun onIncomingPaymentRejected(event: IncomingPaymentRejected) {
        val payerAccount = findBankAccount(event.payerAccountId)
        payerAccount.rejectOutgoingPayment(event.transactionId)
        bankAccountRepository.save(payerAccount)
    }

    @EventHandler
    fun onIncomingPaymentReceived(event: IncomingPaymentReceived) {
        val payerAccount = findBankAccount(event.payerAccountId)
        payerAccount.completeOutgoingPayment(event.transactionId)
        bankAccountRepository.save(payerAccount)
    }

    private fun findBankAccount(accountId: String) = bankAccountRepository.find(accountId)
            ?: throw IllegalStateException("Could not find bank account: $accountId")
}