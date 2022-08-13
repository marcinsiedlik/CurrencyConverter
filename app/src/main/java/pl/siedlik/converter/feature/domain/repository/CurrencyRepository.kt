package pl.siedlik.converter.feature.domain.repository

import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.entity.RatesUpdateDate

interface CurrencyRepository {

  suspend fun getCurrencies(): List<Currency>

  suspend fun getUpdateDate(): RatesUpdateDate?
}