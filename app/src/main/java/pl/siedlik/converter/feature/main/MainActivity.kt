package pl.siedlik.converter.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import pl.siedlik.converter.core.ui.theme.CurrencyConverterTheme
import pl.siedlik.converter.feature.presentation.NavGraphs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setDecorFitsSystemWindows()
    setComposeContent()
  }

  private fun setComposeContent() = setContent {
    CurrencyConverterTheme {
      DestinationsNavHost(navGraph = NavGraphs.root)
    }
  }

  private fun setDecorFitsSystemWindows() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
  }
}