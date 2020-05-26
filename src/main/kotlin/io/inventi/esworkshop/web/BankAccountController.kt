package io.inventi.esworkshop.web

import io.inventi.esworkshop.domain.aggregate.bankaccount.BankAccountFactory
import io.inventi.esworkshop.domain.aggregate.bankaccount.Currency
import io.inventi.esworkshop.domain.repository.BankAccountRepository
import io.inventi.esworkshop.projection.model.BankAccountProjection
import io.inventi.esworkshop.projection.repository.BankAccountProjectionRepository
import io.inventi.esworkshop.service.IdGenerator
import io.inventi.esworkshop.web.model.NewBankAccount
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/accounts")
class BankAccountController(
        private val bankAccountProjectionRepository: BankAccountProjectionRepository,
        private val bankAccountRepository: BankAccountRepository,
        private val bankAccountFactory: BankAccountFactory,
        private val idGenerator: IdGenerator
) {
    @PostMapping
    fun create(@RequestBody newBankAccount: NewBankAccount): ResponseEntity<String> {
        val createdAccount = bankAccountFactory.create(idGenerator.generate())
        createdAccount.register(newBankAccount.name, Currency.valueOf(newBankAccount.currency.name))
        bankAccountRepository.save(createdAccount)

        return ResponseEntity.ok(createdAccount.id)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<BankAccountProjection> {
        return bankAccountProjectionRepository.findByIdOrNull(id)
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAll() = bankAccountProjectionRepository.findAll()
}