package com.voronin.tinkoff.presentation.depositionpoints.models

import android.os.Parcelable
import com.voronin.tinkoff.utils.list.Similarable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageInfo(

    /** mdpi */
    val smallImageUrl: String = "",

    /** hdpi */
    val mediumImageUrl: String = "",

    /** xhdpi */
    val highImageUrl: String = "",

    /** xxhdpi */
    val largeImageUrl: String = "",

) : Parcelable, Similarable<ImageInfo> {

    override fun areItemsTheSame(other: ImageInfo): Boolean {
        return this.smallImageUrl == other.smallImageUrl
    }

    override fun areContentsTheSame(other: ImageInfo): Boolean {
        return this == other
    }
}
