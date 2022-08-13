package pl.siedlik.converter.core.di.module

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemServiceModule {

  @Singleton
  @Provides
  fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
    context.getSystemService(ConnectivityManager::class.java)
}