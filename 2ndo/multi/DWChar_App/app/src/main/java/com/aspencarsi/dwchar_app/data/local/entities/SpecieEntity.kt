package com.aspencarsi.dwchar_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@Entity(tableName = "Species")
data class SpecieEntity (
    @PrimaryKey(autoGenerate= true) val id: Int = 0,
    val description:String,
    //val planetOfOrigin:LocationEntity,
    val image:String
)