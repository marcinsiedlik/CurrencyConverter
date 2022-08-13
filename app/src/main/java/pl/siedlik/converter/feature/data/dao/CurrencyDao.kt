package pl.siedlik.converter.feature.data.dao

import androidx.room.*
import pl.siedlik.converter.feature.data.model.CurrencyModel

@Dao
interface CurrencyDao {

  @Query("SELECT * FROM currencies")
  suspend fun getAll(): List<CurrencyModel>

  @Query("SELECT * FROM currencies WHERE code = :code")
  suspend fun getByCode(code: String): CurrencyModel

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(currencies: List<CurrencyModel>)

  @Delete
  suspend fun delete(currency: CurrencyModel)

  @Query("DELETE FROM currencies")
  suspend fun clear()
}