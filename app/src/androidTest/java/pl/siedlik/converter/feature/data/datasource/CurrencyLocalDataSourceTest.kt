package pl.siedlik.converter.feature.data.datasource

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.siedlik.converter.core.database.AppDatabase
import pl.siedlik.converter.feature.data.dao.CurrencyDao
import pl.siedlik.converter.feature.data.model.CurrencyModel


@ExperimentalCoroutinesApi
class CurrencyLocalDataSourceTest {

  private lateinit var database: AppDatabase
  private lateinit var dao: CurrencyDao

  private val context = InstrumentationRegistry.getInstrumentation().targetContext
  private val testDispatcher = StandardTestDispatcher()
  private val coroutineScope = TestScope(testDispatcher + Job())
  private val datastore = PreferenceDataStoreFactory.create(
    scope = coroutineScope,
    produceFile = { context.preferencesDataStoreFile("test_datastore") }
  )

  private lateinit var testTarget: CurrencyLocalDataSource

  @Before
  fun setUp() {
    database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    dao = database.currencyDao()

    testTarget = CurrencyLocalDataSource(
      dao,
      datastore
    )
  }

  @After
  fun tearDown() {
    coroutineScope.runTest { datastore.edit { it.clear() } }
    coroutineScope.cancel()
    database.close()
  }

  @Test
  fun when_getEffectiveDate_is_called_data_is_gotten_from_datastore() = runTest(testDispatcher) {
    // arrange
    val date = "2020-08-10"
    datastore.edit {
      it[CurrencyLocalDataSource.lastUpdateKey] = date
    }
    // act
    val result = testTarget.getEffectiveDate()
    // assert
    Assert.assertEquals(date, result)
  }

  @Test
  fun when_getEffectiveDate_is_called_and_datastore_is_empty_return_null() = runTest(testDispatcher) {
    // act
    val result = testTarget.getEffectiveDate()
    // assert
    Assert.assertEquals(null, result)
  }

  @Test
  fun when_getCurrencies_is_called_data_is_gotten_form_dao_correctly() = runTest(testDispatcher) {
    // arrange
    val model = listOf(CurrencyModel("USD", "Name", 3.14))
    dao.insertAll(model)
    // act
    val result = testTarget.getCurrencies()
    // assert
    Assert.assertEquals(model, result)
  }

  @Test
  fun when_saveCurrencies_is_called_data_is_putted_into_db_and_datastore() = runTest(testDispatcher) {
    // arrange
    val date = "2020-08-10"
    val model = listOf(CurrencyModel("USD", "Name", 3.14))
    // act
    testTarget.saveCurrencies(date, model)
    // assert
    Assert.assertEquals(model, dao.getAll())
    Assert.assertEquals(date, datastore.data.map { it[CurrencyLocalDataSource.lastUpdateKey] }.first())
  }
}