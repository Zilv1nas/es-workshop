package io.inventi.esworkshop.web

import io.inventi.esworkshop.projection.model.BankAccountProjection
import io.inventi.esworkshop.web.model.NewBankAccount
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/accounts")
class BankAccountController {
    @PostMapping
    fun create(@RequestBody newBankAccount: NewBankAccount): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<BankAccountProjection> {
        TODO("Not yet implemented")
    }

    @GetMapping
    fun getAll(): List<BankAccountProjection> {
        TODO("Not yet implemented")
    }
}