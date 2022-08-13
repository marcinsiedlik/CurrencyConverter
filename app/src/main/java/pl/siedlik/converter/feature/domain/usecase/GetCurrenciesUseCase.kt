package pl.siedlik.converter.feature.domain.usecase

import pl.siedlik.converter.core.util.ResultOf
import pl.siedlik.converter.core.util.resultOf
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
  private val currencyRepository: CurrencyRepository,
) {

  suspend operator fun invoke(): ResultOf<List<Currency>> = resultOf {
    currencyRepository.getCurrencies()
  }
}