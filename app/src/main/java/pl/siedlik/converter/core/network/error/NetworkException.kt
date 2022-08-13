package pl.siedlik.converter.core.network.error

import pl.siedlik.converter.R
import pl.siedlik.converter.core.util.RString
import pl.siedlik.converter.core.util.toRString
import java.io.IOException

sealed class NetworkException(cause: Throwable? = null) : IOException(cause), PrintableMessageException {

  class ConnectivityException(
    override val info: RString = R.string.no_internet_connection.toRString(),
  ) : NetworkException()

  class ServerNotReachableException(
    override val info: RString = R.string.connectivity_exception.toRString(),
  ) : NetworkException()

  class FormatException(
    override val info: RString = R.string.unexpected_error.toRString(),
    cause: Throwable? = null,
  ) : NetworkException(cause)

  class UnknownErrorException(
    override val info: RString = R.string.unexpected_error.toRString(),
    cause: Throwable? = null,
  ) : NetworkException(cause)

  class ErrorResponseException(
    override val info: RString = R.string.unexpected_error.toRString(),
    val errorCode: Int,
  ) : NetworkException()

}