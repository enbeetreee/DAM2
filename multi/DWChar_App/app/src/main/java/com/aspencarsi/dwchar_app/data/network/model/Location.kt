package com.aspencarsi.dwchar_app.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location (
    val id:Int = 0,
    val name:String,
    val type:String,
    val description:String,
    val image:String
    )