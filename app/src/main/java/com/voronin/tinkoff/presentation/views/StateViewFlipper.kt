package com.voronin.tinkoff.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.ViewFlipper
import androidx.appcompat.widget.AppCompatTextView
import com.voronin.tinkoff.R
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.utils.ext.inflate
import com.voronin.tinkoff.utils.ext.isServerError
import kotlinx.android.synthetic.main.view_state_error.view.buttonError
import kotlinx.android.synthetic.main.view_state_error.view.textErrorDescription
import kotlinx.android.synthetic.main.view_state_loading.view.progress

class StateViewFlipper(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    enum class State(val displayedChild: Int) {
        LOADING(0),
        EMPTY(1),
        ERROR(2),
        DATA(3)
    }

    private var state = State.LOADING

    private var loadingView: View = inflate(R.layout.view_state_loading)
    private val progressView: ProgressBar by lazy { loadingView.progress }

    private var emptyView: View = inflate(R.layout.view_state_empty)

    private var errorView: View = inflate(R.layout.view_state_error)
    private val textErrorDescription: AppCompatTextView by lazy { errorView.textErrorDescription }

    init {
        addView(loadingView)
        addView(emptyView)
        addView(errorView)
    }

    fun changeState(operationState: OperationState) {
        when (operationState) {
            is OperationState.Loading -> setStateLoading()
            is OperationState.Success -> setStateData()
            is OperationState.Error -> setStateError(operationState)
            is OperationState.Cancel -> setStateError()
        }
    }

    fun setRetryMethod(retry: () -> Unit) {
        errorView.buttonError.setOnClickListener { retry.invoke() }
    }

    private fun changeState(newState: State) {
        if (state != newState) {
            state = newState
            displayedChild = newState.displayedChild
        }
    }

    fun setStateLoading() {
        changeState(State.LOADING)
    }

    fun setStateEmpty() {
        changeState(State.EMPTY)
    }

    fun setStateError(error: OperationState.Error? = null) {
        changeState(State.ERROR)

        if (error != null && error.e is retrofit2.HttpException) {
            if (error.e.code().isServerError()) {
                textErrorDescription.setText(R.string.error_service)
            }
        }

        textErrorDescription.setText(R.string.error_default_message)
    }

    fun setStateData() {
        changeState(State.DATA)
    }

    fun isLoading() = state == State.LOADING
    fun isEmpty() = state == State.EMPTY
    fun isError() = state == State.ERROR
    fun isData() = state == State.DATA
}
