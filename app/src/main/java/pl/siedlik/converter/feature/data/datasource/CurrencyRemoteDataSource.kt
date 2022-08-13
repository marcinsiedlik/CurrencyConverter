package pl.siedlik.converter.feature.data.datasource

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import pl.siedlik.converter.core.network.datasource.RemoteDataSource
import pl.siedlik.converter.feature.data.model.ExchangeRatesModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRemoteDataSource @Inject constructor(
  private val httpClient: HttpClient,
) : RemoteDataSource {

  suspend fun getExchangeRates(): ExchangeRatesModel = ktorCall {
    val response = httpClient.get("exchangerates/tables/$TableType")
    return@ktorCall response.body<List<ExchangeRatesModel>>().first()
  }

  companion object {
    private const val TableType = "a"
  }
}