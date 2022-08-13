package pl.siedlik.converter.feature.presentation.converter.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyButton(
  code: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Button(
    modifier = modifier
      .padding(start = 16.dp),
    onClick = onClick,
    contentPadding = PaddingValues(vertical = 14.dp)
  ) {
    Text(
      text = code,
      style = MaterialTheme.typography.h6,
    )
  }
}