package pl.siedlik.converter.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import pl.siedlik.converter.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    setupLogging()
  }

  private fun setupLogging() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}