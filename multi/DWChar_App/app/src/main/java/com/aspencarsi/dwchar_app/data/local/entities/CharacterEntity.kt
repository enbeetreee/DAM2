package com.aspencarsi.dwchar_app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "status") val status: String,
    //val species: SpecieEntity,
    //val placeOfOrigin: LocationEntity,
    //@ColumnInfo(name = "jobs") val jobs: List<String>,
    @ColumnInfo(name = "relationsWithTheDoctor") val relationsWithTheDoctor: String,
    //@ColumnInfo(name = "actors") val actors: List<String>,
    @ColumnInfo(name = "image") val image: String
)


