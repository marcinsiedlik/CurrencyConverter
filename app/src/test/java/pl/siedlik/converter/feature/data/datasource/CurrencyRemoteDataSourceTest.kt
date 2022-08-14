package pl.siedlik.converter.feature.data.datasource

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.siedlik.converter.BuildConfig
import pl.siedlik.converter.core.di.module.NetworkModule
import pl.siedlik.converter.core.network.logging.TimberLogger
import pl.siedlik.converter.feature.data.model.CurrencyModel
import pl.siedlik.converter.feature.data.model.ExchangeRatesModel

@ExperimentalCoroutinesApi
class CurrencyRemoteDataSourceTest {

  private lateinit var engine: HttpClientEngine
  private lateinit var httpClient: HttpClient

  private lateinit var testTarget: CurrencyRemoteDataSource

  @Before
  fun setUp() {
    engine = MockEngine { request ->
      when (request.url.toString()) {
        "${BuildConfig.BASE_URL}${CurrencyRemoteDataSource.GetExchangeRateEndpoint}" -> respond(
          content = ByteReadChannel("""[{"table":"A","no":"123","effectiveDate":"2022-08-10","rates":[{"currency":"Name","code":"THB","mid":0.1287}]}]"""),
          status = HttpStatusCode.OK,
          headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
        else -> respondError(status = HttpStatusCode.NotFound)
      }
    }
    httpClient = NetworkModule.provideHttpClient(TimberLogger(), engine)
    testTarget = CurrencyRemoteDataSource(httpClient)
  }

  @After
  fun tearDown() {
    httpClient.close()
  }

  @Test
  fun `when client return response, appropriate model is created`() = runTest {
    // arrange
    val currency = CurrencyModel("THB", "Name", 0.1287)
    val response = ExchangeRatesModel("A", "123", "2022-08-10", listOf(currency))
    // act
    val result = testTarget.getExchangeRates()
    // assert
    Assert.assertEquals(response, result)
  }
}