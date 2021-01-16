package com.voronin.tinkoff.utils.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadUrl(
    url: String?,
    @DrawableRes placeholderRes: Int? = null
) {
    Glide.with(context)
        .load(url)
        .apply { placeholderRes?.let { placeholder(placeholderRes) } }
        .into(this)
}
