package pl.siedlik.converter.feature.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Currency(
  val code: String,
  val name: String,
  val rate: BigDecimal,
) : Parcelable