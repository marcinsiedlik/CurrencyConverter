package pl.siedlik.converter.core.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import pl.siedlik.converter.BuildConfig
import pl.siedlik.converter.core.network.logging.TimberLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun provideLogger(): Logger = TimberLogger()

  @Provides
  @Singleton
  fun provideHttpEngine(): HttpClientEngine = OkHttpEngine(
    config = OkHttpConfig()
  )

  @Singleton
  @Provides
  fun provideHttpClient(logging: Logger, engine: HttpClientEngine): HttpClient = HttpClient(engine) {
    expectSuccess = true

    defaultRequest {
      url(BuildConfig.BASE_URL)
    }
    install(Logging) {
      logger = logging
      level = LogLevel.BODY
    }
    install(ContentNegotiation) {
      json()
    }
  }
}