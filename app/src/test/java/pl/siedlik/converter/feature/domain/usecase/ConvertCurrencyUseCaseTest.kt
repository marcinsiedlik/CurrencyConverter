package pl.siedlik.converter.feature.domain.usecase

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.siedlik.converter.feature.domain.entity.Currency
import java.math.BigDecimal

class ConvertCurrencyUseCaseTest {

  private lateinit var testTarget: ConvertCurrencyUseCase

  @Before
  fun setUp() {
    testTarget = ConvertCurrencyUseCase()
  }

  @Test
  fun `when data is given converted currency is returned`() {
    // arrange
    val from = Currency("", "", BigDecimal(3.142))
    val to = Currency("", "", BigDecimal(4.952))
    // act
    val result = testTarget(from, to, BigDecimal(12.0))
    // assert
    Assert.assertEquals(BigDecimal("7.61"), result)
  }
}