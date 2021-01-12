package com.voronin.tinkoffmap.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ViewFlipper
import androidx.annotation.LayoutRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.voronin.tinkoffmap.R
import com.voronin.tinkoffmap.data.Failure
import com.voronin.tinkoffmap.data.LoadableResult
import com.voronin.tinkoffmap.data.base.NetworkError
import com.voronin.tinkoffmap.data.base.ParsedError
import com.voronin.tinkoffmap.data.Success
import kotlinx.android.synthetic.main.view_state_error.view.buttonError
import kotlinx.android.synthetic.main.view_state_error.view.textErrorDescription
import kotlinx.android.synthetic.main.view_state_error.view.textErrorTitle

class StateViewFlipper(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    enum class State(val displayedChild: Int) {
        LOADING(0),
        ERROR(1),
        DATA(2),
        CUSTOM(3)
    }

    private var state = State.LOADING

    private var loadingView: View
    private val errorView: View

    private val disabledStates = mutableListOf<State>()

    init {
        val layoutInflater = LayoutInflater.from(context)
        val layoutResProvider = LayoutResProvider(context, attrs)

        loadingView = layoutInflater.inflate(layoutResProvider.loadingRes, this, false)
        addView(loadingView)

        errorView = layoutInflater.inflate(layoutResProvider.errorRes, this, false)
        addView(errorView)

        var collapseStatesToTop = false
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.StateViewFlipper)
            collapseStatesToTop = array.getBoolean(R.styleable.StateViewFlipper_collapseStatesToTop, false)
            array.recycle()
        }
        if (collapseStatesToTop) {
            collapseErrorViewToTop()
        }
    }

    fun <T> setStateFromResult(loadableResult: LoadableResult<T>) {
        when (loadableResult) {
            is LoadableResult.Loading -> setStateLoading()
            is Success -> setStateData()
            is Failure -> setStateError(loadableResult.error)
        }
    }

    fun setRetryMethod(retry: () -> Unit) {
        errorView.buttonError.setOnClickListener { retry.invoke() }
    }

    fun setCustomState() {
        changeState(State.CUSTOM)
    }

    fun currentState() = state

    /** Метод деактивирует определенное состояние и не обрабатывает его в changeState() */
    fun disableState(vararg states: State) {
        for (state in states) {
            if (stateIsDisabled(state)) continue
            disabledStates.add(state)
            getChildAt(state.displayedChild)?.isVisible = false
        }
    }

    fun setLoadingView(@LayoutRes layout: Int) {
        removeView(loadingView)
        loadingView = LayoutInflater.from(context).inflate(layout, this, false)
        addView(loadingView, 0)
        changeState(state)
    }

    /** На некоторых экранах необходимо прибить вью ошибки в верхнюю часть экрана, например на экране списка транзакций */
    private fun collapseErrorViewToTop() {
        if (errorView is ConstraintLayout) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(errorView)
            constraintSet.setVerticalBias(R.id.animationViewError, 0f)
            constraintSet.connect(
                R.id.buttonError,
                ConstraintSet.TOP,
                R.id.textErrorDescription,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(errorView)
        }
    }

    private fun changeState(newState: State) {
        if (stateIsDisabled(newState)) return
        if (state != newState || displayedChild != newState.displayedChild) {
            state = newState
            displayedChild = newState.displayedChild
        }
    }

    private fun setStateLoading() {
        changeState(State.LOADING)
    }

    private fun setStateError(error: ParsedError) {
        changeState(State.ERROR)

        when (error) {
            is NetworkError -> setStateNetworkError()
            else -> setGeneralError()
        }
    }

    private fun setStateData() {
        changeState(State.DATA)

    }


    /**
     * Ошибка "Что-то с интернетом"
     */
    private fun setStateNetworkError() {
        setErrorStateContent(
            titleRes = R.string.error_no_network_title,
            descriptionRes = R.string.error_no_network_description,
            errorRes = 0,
        )
    }

    /**
     * Ошибка "Что-то не так"
     */
    private fun setGeneralError() {
        setErrorStateContent(
            titleRes = R.string.error_something_wrong_title,
            descriptionRes = R.string.error_something_wrong_description,
            errorRes = 0
        )
    }

    private fun setErrorStateContent(@StringRes titleRes: Int, @StringRes descriptionRes: Int, @RawRes errorRes: Int) {
        errorView.textErrorTitle?.setText(titleRes)
        errorView.textErrorDescription?.setText(descriptionRes)
    }

    private fun stateIsDisabled(state: State): Boolean {
        return disabledStates.contains(state)
    }

    private class LayoutResProvider(context: Context, attrs: AttributeSet?) {

        companion object {
            @LayoutRes
            val DEFAULT_ERROR_LAYOUT = R.layout.view_state_error

            @LayoutRes
            val DEFAULT_LOADING_LAYOUT = R.layout.view_state_loading
        }

        @LayoutRes val loadingRes: Int
        @LayoutRes val errorRes: Int

        init {
            if (attrs != null) {
                val array = context.obtainStyledAttributes(attrs, R.styleable.StateViewFlipper)

                val collapseStatesToTop = array.getBoolean(R.styleable.StateViewFlipper_collapseStatesToTop, false)
                val loadingId = array.getResourceId(R.styleable.StateViewFlipper_loadingLayoutRes, -1)
                loadingRes = if (loadingId == -1 && collapseStatesToTop) {
                    R.layout.view_state_loading_top
                } else if (loadingId == -1) {
                    DEFAULT_LOADING_LAYOUT
                } else {
                    loadingId
                }
                errorRes = array.getResourceId(
                    R.styleable.StateViewFlipper_errorLayoutRes,
                    DEFAULT_ERROR_LAYOUT
                )

                array.recycle()
            } else {
                loadingRes = DEFAULT_LOADING_LAYOUT
                errorRes = DEFAULT_ERROR_LAYOUT
            }
        }
    }
}
