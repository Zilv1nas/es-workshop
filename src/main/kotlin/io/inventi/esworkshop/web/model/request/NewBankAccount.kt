package io.inventi.esworkshop.web.model.request

import io.inventi.esworkshop.web.model.Currency

data class NewBankAccount(val name: String, val currency: Currency)
