package pl.siedlik.converter.feature.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.siedlik.converter.core.di.annotation.HiltDispatchers
import pl.siedlik.converter.core.network.error.NetworkException
import pl.siedlik.converter.core.util.NetworkInfo
import pl.siedlik.converter.feature.data.datasource.CurrencyLocalDataSource
import pl.siedlik.converter.feature.data.datasource.CurrencyRemoteDataSource
import pl.siedlik.converter.feature.data.mapper.CurrencyMapper
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.domain.entity.RatesUpdateDate
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
  private val remoteDataSource: CurrencyRemoteDataSource,
  private val localDataSource: CurrencyLocalDataSource,
  private val mapper: CurrencyMapper,
  private val networkInfo: NetworkInfo,
  @HiltDispatchers.IO private val coroutineDispatcher: CoroutineDispatcher,
) : CurrencyRepository {

  override suspend fun getCurrencies(): List<Currency> = withContext(coroutineDispatcher) {
    val currencies = when {
      !networkInfo.isAvailable -> localDataSource.getCurrencies()
      else -> try {
        remoteDataSource.getExchangeRates().also { model ->
          localDataSource.saveCurrencies(model.effectiveDate, model.rates)
        }.rates
      } catch (e: NetworkException) {
        localDataSource.getCurrencies()
      }
    }
    return@withContext currencies.map(mapper::mapToEntity)
  }

  override suspend fun getUpdateDate(): RatesUpdateDate? = withContext(coroutineDispatcher) {
    val model = localDataSource.getEffectiveDate()
    return@withContext model?.let(mapper::mapToEntity)
  }
}