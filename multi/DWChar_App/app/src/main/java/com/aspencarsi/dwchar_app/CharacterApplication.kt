package com.aspencarsi.dwchar_app

import android.app.Application
import com.aspencarsi.dwchar_app.data.CharacterRepositoryImpl
import com.aspencarsi.dwchar_app.data.local.provider.CharacterDatabaseProvider
import com.aspen.nasaapod_app.data.local.provider.CharacterNetworkProvider
import com.aspencarsi.dwchar_app.domain.CharacterRepository

class CharacterApplication: Application() {
    lateinit var characterRepository: CharacterRepository

    override fun onCreate() {
        super.onCreate()

        val db = CharacterDatabaseProvider.getDatabase(applicationContext)
        val api = CharacterNetworkProvider.getCharacterApiService()

        characterRepository = CharacterRepositoryImpl(db.characterDao, api)
    }
}