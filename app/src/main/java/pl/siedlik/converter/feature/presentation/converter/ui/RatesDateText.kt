package pl.siedlik.converter.feature.presentation.converter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import pl.siedlik.converter.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RatesDateText(
  date: LocalDate,
  modifier: Modifier = Modifier,
) {
  val formatter = remember {
    DateTimeFormatter.ofPattern("dd MMMM yyy")
  }

  Text(
    modifier = modifier.fillMaxWidth(),
    text = stringResource(id = R.string.rates_from_day, date.format(formatter)),
    style = MaterialTheme.typography.body2,
    textAlign = TextAlign.Center,
  )
}