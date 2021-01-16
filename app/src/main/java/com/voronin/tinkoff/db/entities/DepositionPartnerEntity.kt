package com.voronin.tinkoff.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.voronin.tinkoff.db.dao.DepositionPartnerDao

@Entity(
    tableName = DepositionPartnerDao.TABLE_NAME
)
data class DepositionPartnerEntity(

    @PrimaryKey
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture")
    val picture: String

)
