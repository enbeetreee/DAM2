package com.aspencarsi.dwchar_app.data

import com.aspencarsi.dwchar_app.data.local.dao.CharacterDao
import com.aspencarsi.dwchar_app.data.mappers.toDatabase
import com.aspencarsi.dwchar_app.data.mappers.toDomain
import com.aspencarsi.dwchar_app.data.network.api.CharacterService
import com.aspencarsi.dwchar_app.domain.CharacterRepository
import com.aspencarsi.dwchar_app.domain.model.CharacterM

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class CharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    val characterService: CharacterService
): CharacterRepository {
    override suspend fun fetchCharacters(): Flow<List<CharacterM>> {
        return flow{
            var cachedCharacters = characterDao.getAll().map { it.toDomain() }
            emit(cachedCharacters)

            val characterResponse = characterService.getCharacters()

            characterDao.insertAll(characterResponse.map{it.toDatabase()})

            val updatedCharacters = characterDao.getAll().map { it.toDomain() }
            emit(updatedCharacters)

        }.flowOn(Dispatchers.IO)

    }

    override suspend fun fetchOne(id: String): Flow<CharacterM> {
        return flow{
            var cachedCharacter = characterDao.getOne(id).toDomain()
            emit(cachedCharacter)
        }.flowOn(Dispatchers.IO)

    }



}