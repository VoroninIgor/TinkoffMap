package com.voronin.tinkoff.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ViewFlipper
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.lottie.LottieAnimationView
import com.voronin.tinkoff.R
import com.voronin.tinkoff.data.OperationState
import com.voronin.tinkoff.utils.isServerError
import kotlinx.android.synthetic.main.view_state_empty.view.animationViewEmpty
import kotlinx.android.synthetic.main.view_state_empty.view.imageViewLineToCatalog
import kotlinx.android.synthetic.main.view_state_empty.view.textEmpty
import kotlinx.android.synthetic.main.view_state_error.view.animationViewError
import kotlinx.android.synthetic.main.view_state_error.view.buttonError
import kotlinx.android.synthetic.main.view_state_error.view.textErrorDescription
import kotlinx.android.synthetic.main.view_state_loading.view.animationViewProgress

class StateViewFlipper(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    enum class State(val displayedChild: Int) {
        LOADING(0),
        EMPTY(1),
        ERROR(2),
        DATA(3)
    }

    private var state = State.LOADING

    private lateinit var loadingView: View
    private val animationViewLoading: LottieAnimationView by lazy { loadingView.animationViewProgress }
//     private val lottieAnimationView: LottieAnimationView by lazy { loadingView.lottieAnimationView }

    private lateinit var emptyView: View
    val animationViewEmpty: LottieAnimationView by lazy { emptyView.animationViewEmpty }
    val textEmpty: AppCompatTextView by lazy { emptyView.textEmpty }
    val imageLineToCatalog: AppCompatImageView by lazy { emptyView.imageViewLineToCatalog }
//    val textAsButtonEmpty: AppCompatTextView by lazy { emptyView.textAsButtonEmpty }

    private lateinit var errorView: View
    private val animationViewError: LottieAnimationView by lazy { errorView.animationViewError }
    private val textErrorDescription: AppCompatTextView by lazy { errorView.textErrorDescription }
    // val imageError: AppCompatImageView by lazy { errorView.imageError }
    // val textError: AppCompatTextView by lazy { errorView.textError }
    // val textAsButtonError: AppCompatTextView by lazy { errorView.textAsButtonError }

    private var currentAnimationViewEmpty: LottieAnimationView? = null

    init {
        val layoutInflater = LayoutInflater.from(context)

        loadingView = layoutInflater.inflate(R.layout.view_state_loading, this, false)
        addView(loadingView)

        emptyView = layoutInflater.inflate(R.layout.view_state_empty, this, false)
        addView(emptyView)

        errorView = layoutInflater.inflate(R.layout.view_state_error, this, false)
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
        runAnimationAndStopOthers(animationViewLoading)
    }

    fun setStateEmpty() {
        changeState(State.EMPTY)
        runAnimationAndStopOthers(animationViewEmpty)
    }

    fun setStateError(error: OperationState.Error? = null) {
        changeState(State.ERROR)
        if (error == null) {
            textErrorDescription.setText(R.string.error_default_message)
            animationViewError.setAnimation(R.raw.internet_error)
        } else {
            if (error.e is retrofit2.HttpException) {
                if (error.e.code().isServerError()) {
                    textErrorDescription.setText(R.string.error_service)
                    animationViewError.setAnimation(R.raw.error_server)
                } else {
                    textErrorDescription.setText(R.string.error_default_message)
                    animationViewError.setAnimation(R.raw.internet_error)
                }
            } else {
                textErrorDescription.setText(R.string.error_default_message)
                animationViewError.setAnimation(R.raw.internet_error)
            }
        }
        runAnimationAndStopOthers(animationViewError)
    }

    fun setStateData() {
        changeState(State.DATA)
        runAnimationAndStopOthers()
    }

    /**
     * @param animationView - запускает эту анимацию и останавливает анимации у других состояний. Если null,
     * то останавливает все анимации
     */
    private fun runAnimationAndStopOthers(animationView: LottieAnimationView? = null) {
        if (animationView != animationViewError) {
            animationViewError.pauseAnimation()
        }

        if (animationView != animationViewLoading) {
            animationViewLoading.pauseAnimation()
        }

        if (animationView != animationViewEmpty) {
            animationViewEmpty.pauseAnimation()
        }

        if (currentAnimationViewEmpty != animationView) {
            currentAnimationViewEmpty = animationView
            animationView?.playAnimation()
        }
    }

    fun isLoading() = state == State.LOADING
    fun isEmpty() = state == State.EMPTY
    fun isError() = state == State.ERROR
    fun isData() = state == State.DATA

//    fun setStateInternetError() {
//        // textError.text = context.getString(R.string.error_internet)
//        changeState(State.ERROR)
//    }
//
//    fun setStateUnknownError() {
//        // textError.text = context.getString(R.string.error_unknown)
//        changeState(State.ERROR)
//    }
}
