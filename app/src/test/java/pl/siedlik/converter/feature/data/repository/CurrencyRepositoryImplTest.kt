package pl.siedlik.converter.feature.data.repository

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.siedlik.converter.core.network.error.NetworkException
import pl.siedlik.converter.core.util.NetworkInfo
import pl.siedlik.converter.feature.data.datasource.CurrencyLocalDataSource
import pl.siedlik.converter.feature.data.datasource.CurrencyRemoteDataSource
import pl.siedlik.converter.feature.data.mapper.CurrencyMapper
import pl.siedlik.converter.feature.data.model.CurrencyModel
import pl.siedlik.converter.feature.data.model.ExchangeRatesModel
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.entity.RatesUpdateDate
import java.math.BigDecimal
import java.time.LocalDate

@ExperimentalCoroutinesApi
class CurrencyRepositoryImplTest {

  private val remoteDataSourceMock = mockk<CurrencyRemoteDataSource>()
  private val localDataSourceMock = mockk<CurrencyLocalDataSource>()
  private val mapperMock = mockk<CurrencyMapper>()
  private val networkInfoMock = mockk<NetworkInfo>()
  private val testDispatcher = StandardTestDispatcher()

  private lateinit var testTarget: CurrencyRepositoryImpl

  @Before
  fun setUp() {
    testTarget = CurrencyRepositoryImpl(
      remoteDataSource = remoteDataSourceMock,
      localDataSource = localDataSourceMock,
      mapper = mapperMock,
      networkInfo = networkInfoMock,
      coroutineDispatcher = testDispatcher,
    )
  }

  @Test
  fun `when getCurrencies is called without internet connection, only local data source is invoked`() = runTest(testDispatcher) {
    // arrange
    every { networkInfoMock.isAvailable } returns false
    coEvery { localDataSourceMock.getCurrencies() } returns listOf()
    // act
    testTarget.getCurrencies()
    // assert
    coVerify(exactly = 1) { localDataSourceMock.getCurrencies() }
    coVerify(exactly = 0) { remoteDataSourceMock.getExchangeRates() }
  }

  @Test
  fun `when remote network call returns data, data is passed to local data source and mapper is invoked`() = runTest(testDispatcher) {
    // arrange
    val currency = CurrencyModel("USD", "Name", 3.14)
    val entity = Currency("USD", "Name", BigDecimal(3.14))
    val model = ExchangeRatesModel("A", "", "", listOf(currency))
    every { networkInfoMock.isAvailable } returns true
    coEvery { remoteDataSourceMock.getExchangeRates() } returns model
    coEvery { localDataSourceMock.saveCurrencies(any(), any()) } just Runs
    every { mapperMock.mapToEntity(any<CurrencyModel>()) } returns entity
    // act
    val result = testTarget.getCurrencies()
    // assert
    coVerify(exactly = 1) { remoteDataSourceMock.getExchangeRates() }
    coVerify(exactly = 1) { localDataSourceMock.saveCurrencies(model.effectiveDate, model.rates) }
    verify { mapperMock.mapToEntity(currency) }
    Assert.assertEquals(listOf(entity), result)
  }

  @Test
  fun `when remote network call throws exception, local data source and mapper is invoked`() = runTest(testDispatcher) {
    // arrange
    val currency = CurrencyModel("USD", "Name", 3.14)
    val entity = Currency("USD", "Name", BigDecimal(3.14))
    every { networkInfoMock.isAvailable } returns true
    coEvery { remoteDataSourceMock.getExchangeRates() } throws NetworkException.UnknownErrorException()
    coEvery { localDataSourceMock.getCurrencies() } returns listOf(currency)
    every { mapperMock.mapToEntity(any<CurrencyModel>()) } returns entity
    // act
    val result = testTarget.getCurrencies()
    // assert
    coVerify(exactly = 1) { remoteDataSourceMock.getExchangeRates() }
    coVerify(exactly = 1) { localDataSourceMock.getCurrencies() }
    verify { mapperMock.mapToEntity(currency) }
    Assert.assertEquals(listOf(entity), result)
  }

  @Test
  fun `when getUpdateDate is called, result form local data source is passed to mapper`() = runTest(testDispatcher) {
    // arrange
    val dateString = "2020-08-10"
    val date = RatesUpdateDate(LocalDate.of(2020, 8, 10))
    coEvery { localDataSourceMock.getEffectiveDate() } returns dateString
    every { mapperMock.mapToEntity(any<String>()) } returns date
    // act
    val result = testTarget.getUpdateDate()
    // assert
    verify { mapperMock.mapToEntity(dateString) }
    Assert.assertEquals(date, result)
  }

  @Test
  fun `when local data source returns null mapper is not invoked`() = runTest(testDispatcher) {
    // arrange
    coEvery { localDataSourceMock.getEffectiveDate() } returns null
    // act
    val result = testTarget.getUpdateDate()
    // assert
    verify(exactly = 0) { mapperMock.mapToEntity(any<String>()) }
    Assert.assertEquals(null, result)
  }
}