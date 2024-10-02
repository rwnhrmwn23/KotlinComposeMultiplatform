import androidx.compose.runtime.Composable
import base.State

@Composable
fun <T> State<T>.onIdle(block: @Composable () -> Unit) {
    if (this == State.Idle)
        block.invoke()
}

@Composable
fun <T> State<T>.onLoading(block: @Composable () -> Unit) {
    if (this == State.Loading)
        block.invoke()
}

@Composable
fun <T> State<T>.onSuccess(block: @Composable (T) -> Unit) {
    when (val state = this) {
        is State.Success -> block.invoke(state.data)
        else -> {}
    }
}

@Composable
fun <T> State<T>.onFailure(block: @Composable (Throwable) -> Unit) {
    when (val state = this) {
        is State.Failure -> block.invoke(state.throwable)
        else -> {}
    }
}