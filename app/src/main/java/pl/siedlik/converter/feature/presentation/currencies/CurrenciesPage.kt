package pl.siedlik.converter.feature.presentation.currencies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import pl.siedlik.converter.R
import pl.siedlik.converter.core.ui.ui.AppToolbar
import pl.siedlik.converter.core.ui.ui.ErrorMessagePlaceholder
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.presentation.currencies.ui.CurrenciesList
import pl.siedlik.converter.feature.presentation.currencies.viewmodel.CurrenciesViewModel

@Destination
@Composable
fun CurrenciesPage(
  resultNavigator: ResultBackNavigator<Currency>,
  viewModel: CurrenciesViewModel = hiltViewModel(),
) {
  val state by viewModel.stateFlow.collectAsState()

  Scaffold(
    topBar = {
      AppToolbar(
        text = stringResource(id = R.string.currencies)
      )
    }
  ) {
    when {
      state.isLoading -> Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
      state.errorMessage != null -> state.errorMessage?.let {
        ErrorMessagePlaceholder(
          modifier = Modifier.fillMaxSize(),
          message = it,
          onRetryClick = viewModel::onRetryClick,
        )
      }
      else -> CurrenciesList(
        currencies = state.currencies,
        onItemClick = resultNavigator::navigateBack,
      )
    }
  }
}
