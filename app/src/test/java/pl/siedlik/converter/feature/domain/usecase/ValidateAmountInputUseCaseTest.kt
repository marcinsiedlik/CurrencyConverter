package pl.siedlik.converter.feature.domain.usecase

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateAmountInputUseCaseTest {

  private lateinit var testTarget: ValidateAmountInputUseCase

  @Before
  fun setUp() {
    testTarget = ValidateAmountInputUseCase()
  }

  @Test
  fun `when valid amount is given true is returned`() {
    // arrange
    val input = "3.14"
    // act
    val result = testTarget(input)
    // assert
    Assert.assertEquals(true, result)
  }

  @Test
  fun `when invalid amount is given false is returned`() {
    // arrange
    val input = "3..14"
    // act
    val result = testTarget(input)
    // assert
    Assert.assertEquals(false, result)
  }
}