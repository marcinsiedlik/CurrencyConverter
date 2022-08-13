package pl.siedlik.converter.core.network.datasource

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import pl.siedlik.converter.core.network.error.NetworkException
import timber.log.Timber
import java.net.UnknownHostException

interface RemoteDataSource {

  /// Runs given lambda, catches and maps external libraries exceptions
  /// to internal one, logs development time errors
  suspend fun <T> ktorCall(callLambda: suspend () -> T): T {
    try {
      return callLambda()
    } catch (e: RedirectResponseException) {
      // Got response from backend with 3xx code
      throw NetworkException.ServerNotReachableException()
    } catch (e: ResponseException) {
      // Got response from backend with 3xx-5xx code
      throw NetworkException.ErrorResponseException(errorCode = e.response.status.value)
    } catch (e: UnknownHostException) {
      // cant connect - server down or no internet connection
      throw NetworkException.ServerNotReachableException()
    } catch (e: NoTransformationFoundException) {
      // probably dto is missing @Serializable annotation
      Timber.e(e.message)
      throw NetworkException.FormatException(cause = e)
    } catch (e: DoubleReceiveException) {
      // called body() on response twice
      Timber.e("called body() on response twice: %s", e)
      throw NetworkException.FormatException(cause = e)
    } catch (e: Exception) {
      throw NetworkException.UnknownErrorException(cause = e)
    }
  }
}