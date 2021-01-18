package com.voronin.tinkoff.utils.ext

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).roundToInt()
}

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).roundToInt()
}

fun Int.spToPx(): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics)
        .roundToInt()
}

fun Int?.isServerError(): Boolean {
    return if (this == null) {
        false
    } else {
        this in 500..599
    }
}

fun Int?.isClientError(): Boolean {
    return if (this == null) {
        false
    } else {
        this in 400..499
    }
}

fun Int?.isApiValidationError(): Boolean {
    return this != null && this == 422
}

inline fun Int?.isNullOrZero(): Boolean {
    return this == null || this == 0
}
