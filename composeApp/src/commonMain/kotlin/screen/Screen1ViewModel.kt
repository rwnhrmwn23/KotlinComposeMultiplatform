package screen

import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import base.State
import entity.data.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.ReqresUserRepository

data class Screen1Data(
    val reqresState: State<User> = State.Idle,
)

sealed class Screen1Intent {
    data object GetUser : Screen1Intent()
}

class Screen1ViewModel: BaseViewModel<Screen1Data, Screen1Intent>(Screen1Data()) {
    private val reqresUserRepository = ReqresUserRepository()

    override fun handleIntent(appIntent: Screen1Intent) {
        when(appIntent) {
            is Screen1Intent.GetUser -> getUser()
        }
    }

    private fun getUser() = viewModelScope.launch {
        reqresUserRepository.getUser()
            .stateIn(this)
            .collectLatest { newReqresState ->
                updateModel { model ->
                    model.copy(
                        reqresState = newReqresState
                    )
                }
            }
    }
}