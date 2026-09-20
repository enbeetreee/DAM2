package com.aspencarsi.dwchar_app.data.network.model

import com.squareup.moshi.Json

data class CharacterResponse (
    @Json(name = "id") val id: Int,
    @Json(name = "name")val name:String,
    @Json(name = "description")val description:String,
    @Json(name = "status") val status:String,
    //val species_id:Int,
    //val placeOfOriginId: Int,
    //@Json(name = "jobs")val jobs: List<String>,
    @Json(name = "relationsWithTheDoctor")val relationsWithTheDoctor:String,
    //@Json(name = "actors") val actors: List<String>,
    @Json(name = "image") val image: String
)