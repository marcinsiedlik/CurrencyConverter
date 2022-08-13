package pl.siedlik.converter.core.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class InteractionViewModel<S : ViewState>(initial: S) : ViewModel() {

  private val _stateFlow = MutableStateFlow(initial)
  val stateFlow = _stateFlow.asStateFlow()

  protected var state: S
    get() = _stateFlow.value
    set(value) {
      _stateFlow.tryEmit(value)
    }
}