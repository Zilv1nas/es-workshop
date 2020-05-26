package io.inventi.esworkshop.web.model

import io.inventi.esworkshop.domain.aggregate.bankaccount.Currency

data class NewBankAccount(val name: String, val currency: Currency)
