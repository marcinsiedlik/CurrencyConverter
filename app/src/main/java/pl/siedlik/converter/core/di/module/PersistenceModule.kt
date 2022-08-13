package pl.siedlik.converter.core.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.siedlik.converter.core.database.AppDatabase
import pl.siedlik.converter.feature.data.dao.CurrencyDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  private const val DATASTORE_NAME = "app_datastore"
  private const val DATABASE_NAME = "app_database"

  @Singleton
  @Provides
  fun providePrefDataStore(
    @ApplicationContext context: Context,
  ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
    corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
    produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) },
  )

  @Singleton
  @Provides
  fun provideDatabase(
    @ApplicationContext context: Context,
  ): AppDatabase = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    DATABASE_NAME
  )
    .fallbackToDestructiveMigration()
    .build()

  @Singleton
  @Provides
  fun provideCurrencyDao(database: AppDatabase): CurrencyDao = database.currencyDao()
}