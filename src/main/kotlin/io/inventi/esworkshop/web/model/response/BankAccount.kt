package io.inventi.esworkshop.web.model.response

import io.inventi.esworkshop.web.model.Currency
import java.math.BigDecimal

data class BankAccount(
        val id: String,
        val name: String,
        val currency: Currency,
        val balance: BigDecimal
)