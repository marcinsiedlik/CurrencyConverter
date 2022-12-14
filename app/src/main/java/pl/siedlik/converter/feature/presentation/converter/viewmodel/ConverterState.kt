package pl.siedlik.converter.feature.presentation.converter.viewmodel

import pl.siedlik.converter.core.state.ViewState
import pl.siedlik.converter.core.util.RString
import pl.siedlik.converter.feature.domain.entity.Currency
import java.math.BigDecimal
import java.time.LocalDate

data class ConverterState(
  val isLoading: Boolean,
  val errorMessage: RString?,
  val fromCurrency: Currency,
  val fromAmount: String,
  val toCurrency: Currency,
  val toAmount: BigDecimal?,
  val updateDate: LocalDate?,
) : ViewState {

  companion object {
    fun initial() = ConverterState(
      isLoading = false,
      errorMessage = null,
      fromCurrency = Currency("", "", 0.0.toBigDecimal()),
      fromAmount = "",
      toCurrency = Currency("", "", 0.0.toBigDecimal()),
      toAmount = null,
      updateDate = null,
    )
  }
}