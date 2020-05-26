package io.inventi.esworkshop.web

import io.inventi.esworkshop.domain.repository.BankAccountRepository
import io.inventi.esworkshop.service.IdGenerator
import io.inventi.esworkshop.web.model.request.Deposit
import io.inventi.esworkshop.web.model.request.NewAccountTransaction
import io.inventi.esworkshop.web.model.request.OutgoingPayment
import io.inventi.esworkshop.web.model.response.AccountTransaction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/accounts/{accountId}/transactions")
class AccountTransactionController(
        private val bankAccountRepository: BankAccountRepository,
        private val idGenerator: IdGenerator
) {
    @PostMapping
    fun create(
            @PathVariable("accountId") accountId: String,
            @RequestBody transaction: NewAccountTransaction
    ): ResponseEntity<String> {
        val bankAccount = bankAccountRepository.find(accountId) ?: return ResponseEntity.notFound().build()
        val transactionId = idGenerator.generate()

        return when (transaction) {
            is Deposit -> {
                bankAccount.depositMoney(transactionId, transaction.amount)
                bankAccountRepository.save(bankAccount)
                ResponseEntity.ok(transactionId)
            }
            is OutgoingPayment -> {
                bankAccount.initiateOutgoingPayment(transactionId, transaction.amount, transaction.payeeAccountId)
                bankAccountRepository.save(bankAccount)
                ResponseEntity.ok(transactionId)
            }
        }
    }

    @GetMapping("/{id}")
    fun get(
            @PathVariable("accountId") accountId: String,
            @PathVariable("id") id: String
    ): List<AccountTransaction> {
        TODO("Not yet implemented")
    }

    @GetMapping
    fun getAll(@PathVariable("accountId") accountId: String): List<AccountTransaction> {
        TODO("Not yet implemented")
    }
}