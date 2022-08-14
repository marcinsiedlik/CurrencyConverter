package pl.siedlik.converter.feature.data.mapper

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pl.siedlik.converter.feature.data.model.CurrencyModel
import pl.siedlik.converter.feature.domain.entity.Currency
import java.time.LocalDate

class CurrencyMapperTest {

  private lateinit var testTarget: CurrencyMapper

  @Before
  fun setUp() {
    testTarget = CurrencyMapper()
  }

  @Test
  fun `when Currency mapper is called model with correctly transformed data is returned`() {
    // arrange
    val name = "Name"
    val code = "USD"
    val rate = 3.14
    val model = CurrencyModel(code, name, rate)
    val entity = Currency(code, name, rate.toBigDecimal())
    // act
    val result = testTarget.mapToEntity(model)
    // assert
    Assert.assertEquals(entity, result)
  }

  @Test
  fun `when Date mapper is called model with correctly transformed data is returned`() {
    // arrange
    val dateString = "2020-08-10"
    val date = LocalDate.of(2020, 8, 10)
    // act
    val result = testTarget.mapToEntity(dateString)
    // assert
    Assert.assertEquals(date, result.date)
  }
}