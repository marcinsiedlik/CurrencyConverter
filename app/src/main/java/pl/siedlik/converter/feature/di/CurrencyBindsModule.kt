package pl.siedlik.converter.feature.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.siedlik.converter.feature.data.repository.CurrencyRepositoryImpl
import pl.siedlik.converter.feature.domain.repository.CurrencyRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyBindsModule {

  @Binds
  abstract fun bindCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}