package com.voronin.tinkoff.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.voronin.tinkoff.presentation.depositionpoints.models.ImageInfo
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromModelToJson(model: LocationGeo): String {
        return gson.toJson(model)
    }

    @TypeConverter
    fun fromModelToJson(model: ImageInfo): String {
        return gson.toJson(model)
    }

    @TypeConverter
    fun fromJsonToLocationGeoModel(json: String): LocationGeo {
        return gson.fromJson(json, LocationGeo::class.java)
    }

    @TypeConverter
    fun fromJsonToImageInfoModel(json: String): ImageInfo {
        return gson.fromJson(json, ImageInfo::class.java)
    }
}
