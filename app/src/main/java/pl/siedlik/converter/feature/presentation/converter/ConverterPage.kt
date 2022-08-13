package pl.siedlik.converter.feature.presentation.converter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import pl.siedlik.converter.R
import pl.siedlik.converter.core.ui.ui.AppToolbar
import pl.siedlik.converter.core.ui.ui.ErrorMessagePlaceholder
import pl.siedlik.converter.feature.domain.entity.Currency
import pl.siedlik.converter.feature.presentation.converter.model.CurrencyType
import pl.siedlik.converter.feature.presentation.converter.ui.ConverterFromRow
import pl.siedlik.converter.feature.presentation.converter.ui.ConverterToRow
import pl.siedlik.converter.feature.presentation.converter.ui.RatesDateText
import pl.siedlik.converter.feature.presentation.converter.viewmodel.ConverterViewModel
import pl.siedlik.converter.feature.presentation.destinations.CurrenciesPageDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun ConverterPage(
  navigator: DestinationsNavigator,
  resultRecipient: ResultRecipient<CurrenciesPageDestination, Currency>,
  viewModel: ConverterViewModel = hiltViewModel(),
) {
  val state by viewModel.stateFlow.collectAsState()

  resultRecipient.onNavResult { result ->
    if (result is NavResult.Value) {
      viewModel.onCurrencySelected(result.value)
    }
  }

  Scaffold(
    topBar = {
      AppToolbar(
        text = stringResource(id = R.string.currency_converter)
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
      else -> Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
      ) {
        state.updateDate?.let { date ->
          RatesDateText(
            modifier = Modifier.padding(24.dp),
            date = date,
          )
        }

        ConverterFromRow(
          currencyCode = state.fromCurrency.code,
          amount = state.fromAmount,
          onAmountChange = viewModel::onAmountInputChange,
          onCurrencyClick = {
            viewModel.onCurrencyTap(CurrencyType.From)
            navigator.navigate(CurrenciesPageDestination)
          },
        )

        Spacer(modifier = Modifier.height(24.dp))

        ConverterToRow(
          currencyCode = state.toCurrency.code,
          amount = state.toAmount,
          onCurrencyClick = {
            viewModel.onCurrencyTap(CurrencyType.To)
            navigator.navigate(CurrenciesPageDestination)
          },
        )
      }
    }
  }
}
