package com.aspencarsi.dwchar_app.data.network.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SpecieResponse (
    val id:Int,
    val description:String,
   // val planetOfOrigin:Location,
    val image:String
)