package pl.siedlik.converter.feature.domain.usecase

import pl.siedlik.converter.core.util.ResultOf
import pl.siedlik.converter.core.util.resultOf
import pl.siedlik.converter.feature.domain.entity.RatesUpdateDate
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetRatesUpdateDateUseCase @Inject constructor(
  private val currencyRepository: CurrencyRepository,
) {

  suspend operator fun invoke(): ResultOf<RatesUpdateDate?> = resultOf {
    currencyRepository.getUpdateDate()
  }
}