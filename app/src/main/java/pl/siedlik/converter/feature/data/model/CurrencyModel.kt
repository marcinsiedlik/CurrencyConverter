package pl.siedlik.converter.feature.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "currencies")
data class CurrencyModel(
  @PrimaryKey
  val code: String,
  val currency: String,
  val mid: Double,
)