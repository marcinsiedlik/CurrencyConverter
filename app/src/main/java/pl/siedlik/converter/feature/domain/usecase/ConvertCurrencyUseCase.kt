package pl.siedlik.converter.feature.domain.usecase

import pl.siedlik.converter.feature.domain.entity.Currency
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor() {

  operator fun invoke(from: Currency, to: Currency, amount: BigDecimal): BigDecimal {
    val rate = from.rate / to.rate
    val result = amount * rate
    return result.setScale(2, RoundingMode.HALF_EVEN)
  }
}