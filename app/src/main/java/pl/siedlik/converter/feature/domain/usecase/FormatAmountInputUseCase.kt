package pl.siedlik.converter.feature.domain.usecase

import javax.inject.Inject

class FormatAmountInputUseCase @Inject constructor() {

  operator fun invoke(old: String, new: String): String {
    val dotOnly = new.replace(',', '.')
    if (dotOnly.matches(regex)) {
      return dotOnly
    }
    return old
  }

  companion object {
    private val regex = Regex("^\\d*\\.?\\d{0,2}\$")
  }
}