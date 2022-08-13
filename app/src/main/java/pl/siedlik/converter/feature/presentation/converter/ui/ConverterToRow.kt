package pl.siedlik.converter.feature.presentation.converter.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConverterToRow(
  modifier: Modifier = Modifier,
  currencyCode: String,
  amount: Double?,
  onCurrencyClick: () -> Unit,
) {
  Row(
    modifier = modifier.padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      modifier = Modifier.weight(3f),
      text = amount?.let { "%,.2f".format(it) } ?: "-,--",
      fontSize = 24.sp,
      fontWeight = FontWeight.Medium,
      maxLines = 1,
    )
    CurrencyButton(
      modifier = Modifier.weight(1f),
      code = currencyCode,
      onClick = onCurrencyClick,
    )
  }
}