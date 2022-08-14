package pl.siedlik.converter.feature.data.mapper

import pl.siedlik.converter.feature.data.model.CurrencyModel
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.entity.RatesUpdateDate
import java.time.LocalDate
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {

  fun mapToEntity(currencyModel: CurrencyModel) = Currency(
    code = currencyModel.code,
    name = currencyModel.currency,
    rate = currencyModel.mid.toBigDecimal(),
  )

  fun mapToEntity(dateModel: String) = RatesUpdateDate(
    date = LocalDate.parse(dateModel)
  )
}