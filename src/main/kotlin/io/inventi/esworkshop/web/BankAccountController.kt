package io.inventi.esworkshop.web

import io.inventi.esworkshop.web.model.response.BankAccount
import io.inventi.esworkshop.web.model.request.NewBankAccount
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
    fun create(@RequestBody newBankAccount: NewBankAccount): BankAccount {
        TODO("Not yet implemented")
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String) {
        TODO("Not yet implemented")
    }
}