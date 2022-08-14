package pl.siedlik.converter.feature.domain.usecase

import java.math.BigDecimal
import javax.inject.Inject

class ConvertStringToDecimalUseCase @Inject constructor() {

  operator fun invoke(input: String): BigDecimal? = input.toBigDecimalOrNull()
}