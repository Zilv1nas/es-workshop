package io.inventi.esworkshop.web

import io.inventi.esworkshop.web.model.request.NewAccountTransaction
import io.inventi.esworkshop.web.model.response.AccountTransaction
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/accounts/{accountId}/transactions")
class AccountTransactionController {
    @PostMapping
    fun create(
            @PathVariable("accountId") accountId: String,
            @RequestBody transaction: NewAccountTransaction
    ): AccountTransaction {
        TODO("Not yet implemented")
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