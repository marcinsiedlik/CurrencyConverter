package pl.siedlik.converter.core.util

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import kotlinx.parcelize.Parcelize

sealed interface RString : Parcelable {

  fun get(context: Context): String

  @Parcelize
  data class Plain(val text: String) : RString {
    override fun get(context: Context) = text
  }

  @Parcelize
  data class Res(@StringRes val resId: Int) : RString {
    override fun get(context: Context): String = context.getString(resId)
  }

  companion object {
    fun plain(text: String) = Plain(text)
    fun res(@StringRes resId: Int) = Res(resId)
  }
}

fun String.toRString() = RString.plain(this)
fun @receiver:StringRes Int.toRString() = RString.res(this)

@Composable
@ReadOnlyComposable
fun rString(string: RString): String = string.get(LocalContext.current)

