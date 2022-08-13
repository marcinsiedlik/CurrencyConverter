package pl.siedlik.converter.feature.presentation.currencies.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.siedlik.converter.feature.domain.entity.Currency

@Composable
fun CurrenciesList(
  currencies: List<Currency>,
  onItemClick: (Currency) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(modifier = modifier) {
    items(currencies) { item ->
      CurrencyItem(
        item = item,
        onClick = { onItemClick(item) },
      )
    }
  }
}