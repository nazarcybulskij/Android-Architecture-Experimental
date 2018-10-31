package my.cybulski.nazar.architecture_experemetal.usecase

import android.os.Parcel
import android.os.Parcelable
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.channels.consumeEach
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext


open class ViewState() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ViewState> {
        override fun createFromParcel(parcel: Parcel): ViewState {
            return ViewState(parcel)
        }

        override fun newArray(size: Int): Array<ViewState?> {
            return arrayOfNulls(size)
        }
    }
}

class BaseUseCase<S : ViewState, T>(
        private val machine: ViewStateMachine<S>,
        private val block: suspend CoroutineScope.() -> T
) {

    private var startedReducer: (S.() -> S)? = null
    private var failureReducer: (S.(Exception) -> S)? = null
    private var successReducer: (S.(T) -> S)? = null
    private var finallyReducer: (S.() -> S)? = null


    fun started(reducer: S.() -> S): BaseUseCase<S, T> {
        startedReducer = reducer
        return this
    }

    fun failure(reducer: S.(Exception) -> S): BaseUseCase<S, T> {
        failureReducer = reducer
        return this
    }

    fun success(reducer: S.(T) -> S): BaseUseCase<S, T> {
        successReducer = reducer
        return this
    }

    fun finally(reducer: S.() -> S): BaseUseCase<S, T> {
        finallyReducer = reducer
        return this
    }

    fun start(): Job = launch(CommonPool) {
        startedReducer?.let(machine::sendState)
        try {
            val value = block()
            successReducer?.let { machine.sendState { it(value) } }
        } catch (e: Exception) {
            failureReducer?.let { machine.sendState { it(e) } }
        }
        finallyReducer?.let(machine::sendState)
    }


}


abstract class  ViewStateMachine< S : ViewState>(
        initialState: S,
        override val coroutineContext: CoroutineContext = Job()):CoroutineScope{


    private val broadcast = ConflatedBroadcastChannel(initialState)

    fun sendState(reducer: S.() -> S) {
        broadcast.offer(reducer(broadcast.value))
    }


    fun <T> task(block: suspend CoroutineScope.() -> T) = BaseUseCase(this, block)

}