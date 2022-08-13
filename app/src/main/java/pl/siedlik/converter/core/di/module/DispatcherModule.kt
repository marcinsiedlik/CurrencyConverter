package pl.siedlik.converter.core.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pl.siedlik.converter.core.di.annotation.HiltDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

  @HiltDispatchers.Main
  @Singleton
  @Provides
  fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

  @HiltDispatchers.Default
  @Singleton
  @Provides
  fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

  @HiltDispatchers.IO
  @Singleton
  @Provides
  fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

  @HiltDispatchers.Unconfined
  @Singleton
  @Provides
  fun provideDispatcherUnconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}