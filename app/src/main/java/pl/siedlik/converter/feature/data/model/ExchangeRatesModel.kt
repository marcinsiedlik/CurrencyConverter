package pl.siedlik.converter.feature.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesModel(
  val table: String,
  val no: String,
  val effectiveDate: String,
  val rates: List<CurrencyModel>,
)