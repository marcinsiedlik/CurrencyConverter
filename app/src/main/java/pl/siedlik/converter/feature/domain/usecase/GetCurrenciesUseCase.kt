package pl.siedlik.converter.feature.domain.usecase

import pl.siedlik.converter.core.util.ResultOf
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
  private val currencyRepository: CurrencyRepository,
) {

  suspend operator fun invoke(): ResultOf<List<Currency>> = try {
    val result = currencyRepository.getCurrencies()
    if (result.size < 2) {
      throw IllegalStateException("No enough currencies")
    }
    ResultOf.success(result)
  } catch (e: Exception) {
    ResultOf.failure(e)
  }
}