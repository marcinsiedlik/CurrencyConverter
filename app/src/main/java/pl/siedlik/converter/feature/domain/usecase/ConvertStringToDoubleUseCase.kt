package pl.siedlik.converter.feature.domain.usecase

import javax.inject.Inject

class ConvertStringToDoubleUseCase @Inject constructor() {

  operator fun invoke(input: String): Double? = input.toDoubleOrNull()
}