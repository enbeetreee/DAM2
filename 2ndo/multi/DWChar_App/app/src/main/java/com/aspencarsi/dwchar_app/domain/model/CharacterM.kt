package com.aspencarsi.dwchar_app.domain.model


data class CharacterM (
    val id:Int,
    val name:String,
    val description:String,
    val status:String,
    //val species: Specie,
    //val placeOfOrigin: Location,
   // val jobs: List<String>,
    val relationsWithTheDoctor:String,
    //val actors: List<String>,
    val image: String
)