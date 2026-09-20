package com.aspencarsi.dwchar_app.domain
import com.aspencarsi.dwchar_app.domain.model.CharacterM
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun fetchCharacters(): Flow<List<CharacterM>>
    suspend fun fetchOne(name:String): Flow<CharacterM>
}