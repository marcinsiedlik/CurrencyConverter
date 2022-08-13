package pl.siedlik.converter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.siedlik.converter.feature.data.dao.CurrencyDao
import pl.siedlik.converter.feature.data.model.CurrencyModel

@Database(
  entities = [CurrencyModel::class],
  version = 1,
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun currencyDao(): CurrencyDao
}