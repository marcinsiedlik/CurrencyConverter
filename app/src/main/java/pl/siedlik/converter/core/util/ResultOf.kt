package pl.siedlik.converter.core.util

import pl.siedlik.converter.R
import pl.siedlik.converter.core.network.error.PrintableMessageException

sealed class ResultOf<T> {
  data class Success<T>(val data: T) : ResultOf<T>()
  data class Failure<T>(val cause: Throwable) : ResultOf<T>() {

    fun getErrorMessage(
      default: RString = R.string.unexpected_error.toRString(),
    ): RString {
      if (cause is PrintableMessageException) {
        return cause.info
      }
      return default
    }
  }

  fun getOrNull() = if (this is Success) data else null

  companion object {
    fun success() = Success(Unit)
    fun <T> success(data: T) = Success(data)
    fun <T> failure(cause: Throwable) = Failure<T>(cause)
  }
}

suspend fun <T> resultOf(action: suspend () -> T): ResultOf<T> = try {
  ResultOf.success(action())
} catch (e: Exception) {
  ResultOf.failure(e)
}