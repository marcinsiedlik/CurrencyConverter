package pl.siedlik.converter.feature.presentation.currencies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.siedlik.converter.feature.domain.entity.Currency

@Composable
fun CurrencyItem(
  item: Currency,
  onClick: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp, vertical = 4.dp),
    elevation = 1.dp,
  ) {
    Column(
      modifier = Modifier
        .clickable(onClick = onClick)
        .padding(8.dp),
    ) {
      Text(text = item.code, fontWeight = FontWeight.Medium)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = item.name)
    }
  }
}