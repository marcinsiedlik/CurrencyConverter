package pl.siedlik.converter.feature.domain.usecase

import pl.siedlik.converter.feature.domain.entity.Currency
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor() {

  operator fun invoke(from: Currency, to: Currency, amount: Double): Double {
    val rate = from.rate / to.rate
    return amount * rate
  }
}