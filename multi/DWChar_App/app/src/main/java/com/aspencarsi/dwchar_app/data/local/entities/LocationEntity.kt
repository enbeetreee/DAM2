package com.aspencarsi.dwchar_app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Locations")
data class LocationEntity (
    @PrimaryKey(autoGenerate= true) val id: Int = 0,

    val name:String,
    val type:String,
    val description:String,
    val image:String
    )