package pl.siedlik.converter.core.network.logging

import io.ktor.client.plugins.logging.*
import timber.log.Timber

class TimberLogger : Logger {

  override fun log(message: String) {
    Timber.d(message)
  }
}