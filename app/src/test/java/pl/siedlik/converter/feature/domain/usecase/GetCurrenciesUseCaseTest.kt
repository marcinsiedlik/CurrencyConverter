package pl.siedlik.converter.feature.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.siedlik.converter.core.network.error.NetworkException
import pl.siedlik.converter.core.util.ResultOf
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class GetCurrenciesUseCaseTest {

  private val currencyRepositoryMock = mockk<CurrencyRepository>()

  private lateinit var testTarget: GetCurrenciesUseCase

  @Before
  fun setUp() {
    testTarget = GetCurrenciesUseCase(currencyRepositoryMock)
  }

  @Test
  fun `when repository return value success result is returned`() = runTest {
    // arrange
    val data = listOf(
      Currency("USD", "Name", BigDecimal("3.14")),
      Currency("USD", "Name", BigDecimal("3.14")),
    )
    coEvery { currencyRepositoryMock.getCurrencies() } returns data
    // act
    val result = testTarget()
    // assert
    Assert.assertEquals(ResultOf.success(data), result)
  }

  @Test
  fun `when repository returns not enough data, failure result is returned`() = runTest {
    // arrange
    val data = listOf(Currency("USD", "Name", BigDecimal("3.14")))
    coEvery { currencyRepositoryMock.getCurrencies() } returns data
    // act
    val result = testTarget()
    // assert
    Assert.assertTrue(result is ResultOf.Failure && result.cause is IllegalStateException)
  }

  @Test
  fun `when repository throws exception failure result is returned`() = runTest {
    // arrange
    val exception = NetworkException.UnknownErrorException()
    coEvery { currencyRepositoryMock.getCurrencies() } throws exception
    // act
    val result = testTarget()
    // assert
    Assert.assertEquals(ResultOf.failure<List<Currency>>(exception), result)
  }
}