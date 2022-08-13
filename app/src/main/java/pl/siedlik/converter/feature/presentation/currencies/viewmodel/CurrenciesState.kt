package pl.siedlik.converter.feature.presentation.currencies.viewmodel

import pl.siedlik.converter.core.state.ViewState
import pl.siedlik.converter.core.util.RString
import pl.siedlik.converter.feature.domain.entity.Currency

data class CurrenciesState(
  val isLoading: Boolean,
  val errorMessage: RString?,
  val currencies: List<Currency>,
) : ViewState {


  companion object {
    fun initial() = CurrenciesState(
      isLoading = false,
      errorMessage = null,
      currencies = listOf(),
    )
  }
}