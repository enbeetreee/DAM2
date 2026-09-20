package com.aspencarsi.dwchar_app.data.network.api

import com.aspencarsi.dwchar_app.data.network.model.CharacterResponse
import retrofit2.http.GET


interface CharacterService {

    companion object {
        const val PAGINATED_CHARACTERS = 1
    }

    @GET("character")
    suspend fun getCharacters() : List<CharacterResponse>

}