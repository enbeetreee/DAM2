package com.aspencarsi.dwchar_app.data.mappers

import com.aspencarsi.dwchar_app.data.local.entities.CharacterEntity
import com.aspencarsi.dwchar_app.data.network.model.CharacterResponse
import com.aspencarsi.dwchar_app.domain.model.CharacterM

fun CharacterResponse.toDomain() = CharacterM(
    id,
    name,
    description,
    status,
    //species,
    //placeOfOrigin,
   // jobs,
    relationsWithTheDoctor,
    //actors,
    image)
fun CharacterResponse.toDatabase() = CharacterEntity(
    id,
    name,
    description,
    status,
    //species,
    //placeOfOrigin,
  //  jobs,
    relationsWithTheDoctor,
    //actors,
    image)

fun CharacterEntity.toDomain() = CharacterM(
    id,
    name,
    description,
    status,
    //species,
    //placeOfOrigin,
   // jobs,
    relationsWithTheDoctor,
    //actors,
    image)

