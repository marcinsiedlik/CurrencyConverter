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
import pl.siedlik.converter.feature.domain.entity.RatesUpdateDate
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository
import java.time.LocalDate

@ExperimentalCoroutinesApi
class GetRatesUpdateDateUseCaseTest {

  private val currencyRepositoryMock = mockk<CurrencyRepository>()

  private lateinit var testTarget: GetRatesUpdateDateUseCase

  @Before
  fun setUp() {
    testTarget = GetRatesUpdateDateUseCase(currencyRepositoryMock)
  }

  @Test
  fun `when repository return value success result is returned`() = runTest {
    // arrange
    val data = RatesUpdateDate(LocalDate.of(2020, 8, 10))
    coEvery { currencyRepositoryMock.getUpdateDate() } returns data
    // act
    val result = testTarget()
    // assert
    Assert.assertEquals(ResultOf.success(data), result)
  }

  @Test
  fun `when repository throws exception failure result is returned`() = runTest {
    // arrange
    val exception = NetworkException.UnknownErrorException()
    coEvery { currencyRepositoryMock.getUpdateDate() } throws exception
    // act
    val result = testTarget()
    // assert
    Assert.assertEquals(ResultOf.failure<RatesUpdateDate>(exception), result)
  }
}