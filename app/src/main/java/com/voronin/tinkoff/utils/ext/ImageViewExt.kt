package com.voronin.tinkoff.utils.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.voronin.tinkoff.R

fun ImageView.loadUrl(
    url: String?,
    @DrawableRes placeholderRes: Int = R.drawable.ic_img_placeholder,
    roundedCornerDim: Int? = null,
) {
    Glide.with(context)
        .load(url)
        .apply {
            placeholder(placeholderRes)
            roundedCornerDim?.let {
                apply(RequestOptions.bitmapTransform(RoundedCorners(it)))
            }
        }
        .into(this)
}
