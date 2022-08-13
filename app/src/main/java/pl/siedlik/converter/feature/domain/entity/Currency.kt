package pl.siedlik.converter.feature.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
  val code: String,
  val name: String,
  val rate: Double,
) : Parcelable