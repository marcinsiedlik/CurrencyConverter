package pl.siedlik.converter.core.ui.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.siedlik.converter.R
import pl.siedlik.converter.core.util.RString
import pl.siedlik.converter.core.util.rString

@Composable
fun ErrorMessagePlaceholder(
  message: RString,
  onRetryClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.padding(24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Icon(
      modifier = Modifier.size(70.dp),
      imageVector = Icons.Default.Warning,
      contentDescription = stringResource(id = R.string.error_icon),
      tint = Color.Gray,
    )
    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = rString(message)
    )

    if (onRetryClick != null) {
      Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = onRetryClick,
      ) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.try_again))
      }
    }
  }
}