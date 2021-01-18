package com.voronin.tinkoff.utils.ext

fun Int?.isServerError(): Boolean {
    return if (this == null) {
        false
    } else {
        this in 500..599
    }
}
