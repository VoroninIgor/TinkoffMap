package com.voronin.api.dto

data class ImageInfoDto(

    /** mdpi */
    val smallImageUrl: String = "",

    /** hdpi */
    val mediumImageUrl: String = "",

    /** xhdpi */
    val highImageUrl: String = "",

    /** xxhdpi */
    val largeImageUrl: String = "",

)