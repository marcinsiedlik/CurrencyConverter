package pl.siedlik.converter.feature.presentation.converter.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ConverterFromRow(
  modifier: Modifier = Modifier,
  currencyCode: String,
  amount: String,
  onAmountChange: (String) -> Unit,
  onCurrencyClick: () -> Unit,
) {
  Row(
    modifier = modifier.padding(horizontal = 16.dp),
  ) {
    OutlinedTextField(
      modifier = Modifier.weight(3f),
      value = amount,
      onValueChange = onAmountChange,
      keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        keyboardType = KeyboardType.Number,
      )
    )
    CurrencyButton(
      modifier = Modifier.weight(1f),
      code = currencyCode,
      onClick = onCurrencyClick,
    )
  }
}