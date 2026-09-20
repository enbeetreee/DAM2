package com.aspencarsi.dwchar_app.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithSpecieNLocation(
    @Embedded
    val character: CharacterEntity,

    @Relation(
        parentColumn = "id",
        entity = SpecieEntity::class,
        entityColumn = "species_id",
    )
    val species: SpecieEntity,

    @Relation(
        parentColumn = "id",
        entity = LocationEntity::class,
        entityColumn = "location_id",
    )
    val placeOfOrigin: LocationEntity

)
