package pl.siedlik.converter.core.ui.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppToolbar(
  text: String,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
  ) {
    Box(
      modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()
        .height(56.dp)
    ) {
      Text(
        modifier = Modifier.align(Alignment.Center),
        text = text,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
      )
    }
  }
}