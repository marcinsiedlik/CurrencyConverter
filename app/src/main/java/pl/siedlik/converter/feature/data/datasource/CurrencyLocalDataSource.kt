package pl.siedlik.converter.feature.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.siedlik.converter.core.network.datasource.LocalDataSource
import pl.siedlik.converter.feature.data.dao.CurrencyDao
import pl.siedlik.converter.feature.data.model.CurrencyModel

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyLocalDataSource @Inject constructor(
  private val currencyDao: CurrencyDao,
  private val dataStore: DataStore<Preferences>,
) : LocalDataSource {

  suspend fun getCurrencies(): List<CurrencyModel> = currencyDao.getAll()

  suspend fun getEffectiveDate(): String? = dataStore.data.map { preferences ->
    preferences[lastUpdateKey]
  }.first()

  suspend fun saveCurrencies(date: String, currencies: List<CurrencyModel>) {
    currencyDao.insertAll(currencies)

    dataStore.edit { preferences ->
      preferences[lastUpdateKey] = date
    }
  }

  companion object {
    private val lastUpdateKey = stringPreferencesKey("currency_last_update")
  }
}