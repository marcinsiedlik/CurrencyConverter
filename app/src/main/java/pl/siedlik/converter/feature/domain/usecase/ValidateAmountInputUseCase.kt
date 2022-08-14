package pl.siedlik.converter.feature.domain.usecase

import javax.inject.Inject

class ValidateAmountInputUseCase @Inject constructor() {

  operator fun invoke(value: String): Boolean = value.matches(regex)

  companion object {
    private val regex = Regex("^\\d*\\.?\\d{0,2}\$")
  }
}