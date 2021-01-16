package com.voronin.tinkoff.data.mappers

import com.voronin.tinkoff.presentation.depositionpoints.models.ImageInfo
import javax.inject.Inject

class ImageUrlMapper @Inject constructor() {

    companion object {
        private const val MDPI = "mdpi"
        private const val HDPI = "hdpi"
        private const val XDPI = "hdpi"
        private const val XXHDPI = "xxhdpi"
    }

    fun getImageUrl(partnerName: String): ImageInfo {
        return ImageInfo(
            smallImageUrl = partnerName.getPartnerImageUrl(MDPI),
            mediumImageUrl = partnerName.getPartnerImageUrl(HDPI),
            highImageUrl = partnerName.getPartnerImageUrl(XDPI),
            largeImageUrl = partnerName.getPartnerImageUrl(XXHDPI),
        )
    }

    private fun String.getPartnerImageUrl(dpi: String): String {
        return "https://static.tinkoff.ru/icons/deposition-partners-v3/$dpi/${this.toLowerCase()}"
    }
}
