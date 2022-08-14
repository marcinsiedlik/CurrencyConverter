package pl.siedlik.converter.feature.presentation.converter.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.siedlik.converter.core.state.InteractionViewModel
import pl.siedlik.converter.core.util.ResultOf
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.usecase.*
import pl.siedlik.converter.feature.presentation.converter.model.CurrencyType
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase,
  private val getUpdateDateUseCase: GetRatesUpdateDateUseCase,
  private val validateAmountInputUseCase: ValidateAmountInputUseCase,
  private val convertStringToDecimalUseCase: ConvertStringToDecimalUseCase,
  private val convertCurrencyUseCase: ConvertCurrencyUseCase,
) : InteractionViewModel<ConverterState>(ConverterState.initial()) {

  private var lastType: CurrencyType? = null

  init {
    fetchCurrencies()
  }

  fun onRetryClick() {
    fetchCurrencies()
  }

  fun onCurrencyTap(type: CurrencyType) {
    lastType = type
  }

  fun onCurrencySelected(currency: Currency) {
    lastType?.let {
      state = when (it) {
        CurrencyType.From -> state.copy(
          fromCurrency = currency,
          toAmount = convertRates(from = currency, input = state.fromAmount),
        )
        CurrencyType.To -> state.copy(
          toCurrency = currency,
          toAmount = convertRates(to = currency, input = state.fromAmount),
        )
      }
    }
  }

  fun onAmountInputChange(value: String) {
    val dotOnly = value.replace(',', '.')
    if (validateAmountInputUseCase(dotOnly)) {
      state = state.copy(
        fromAmount = dotOnly,
        toAmount = convertRates(input = dotOnly)
      )
    }
  }

  private fun convertRates(
    from: Currency = state.fromCurrency,
    to: Currency = state.toCurrency,
    input: String,
  ): BigDecimal? {
    val fromAmount = convertStringToDecimalUseCase(input) ?: return null
    return convertCurrencyUseCase(from, to, fromAmount)
  }

  private fun fetchCurrencies() {
    viewModelScope.launch {
      state = state.copy(isLoading = true)
      state = when (val result = getCurrenciesUseCase()) {
        is ResultOf.Success -> state.copy(
          isLoading = false,
          errorMessage = null,
          fromCurrency = result.data.first(),
          toCurrency = result.data[1],
          updateDate = getUpdateDateUseCase().getOrNull()?.date,
        )
        is ResultOf.Failure -> state.copy(
          isLoading = false,
          errorMessage = result.getErrorMessage(),
        )
      }
    }
  }
}
