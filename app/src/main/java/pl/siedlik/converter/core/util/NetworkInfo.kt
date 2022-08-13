package pl.siedlik.converter.core.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInfo @Inject constructor(
  private val connectivityManager: ConnectivityManager,
) {

  val isAvailable: Boolean
    get() = connectionCheckImpl()

  private fun connectionCheckImpl(): Boolean = connectivityManager
    .getNetworkCapabilities(connectivityManager.activeNetwork)
    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    ?: false
}