package pl.siedlik.converter.feature.presentation.currencies.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.siedlik.converter.core.state.InteractionViewModel
import pl.siedlik.converter.core.util.ResultOf
import pl.siedlik.converter.feature.domain.usecase.GetCurrenciesUseCase
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase,
) : InteractionViewModel<CurrenciesState>(CurrenciesState.initial()) {

  private var currentJob: Job? = null

  init {
    fetchCurrencies()
  }

  fun onRetryClick() {
    fetchCurrencies()
  }

  private fun fetchCurrencies() {
    currentJob?.cancel()
    currentJob = viewModelScope.launch {
      state = state.copy(isLoading = true)
      state = when (val result = getCurrenciesUseCase()) {
        is ResultOf.Success -> state.copy(
          isLoading = false,
          errorMessage = null,
          currencies = result.data,
        )
        is ResultOf.Failure -> state.copy(
          isLoading = false,
          errorMessage = result.getErrorMessage(),
        )
      }
    }
  }
}