package pl.siedlik.converter.feature.domain.usecase

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ConvertStringToDecimalUseCaseTest {

  private lateinit var testTarget: ConvertStringToDecimalUseCase

  @Before
  fun setUp() {
    testTarget = ConvertStringToDecimalUseCase()
  }

  @Test
  fun `when valid data is given, decimal representation is returned`() {
    // arrange
    val input = "3.14"
    // act
    val result = testTarget(input)
    // assert
    Assert.assertEquals(BigDecimal("3.14"), result)
  }

  @Test
  fun `when invalid data is given, null is returned`() {
    // arrange
    val input = "3.n"
    // act
    val result = testTarget(input)
    // assert
    Assert.assertEquals(null, result)
  }
}