package com.aspencarsi.dwchar_app.data.local.provider

import android.content.Context
import androidx.room.Room
import com.aspencarsi.dwchar_app.data.local.CharacterDatabase

object CharacterDatabaseProvider {
    private  val DB_FILENAME = "characters.db"

    fun getDatabase(context: Context) =
        Room
            .databaseBuilder(context, CharacterDatabase::class.java, DB_FILENAME)
            .fallbackToDestructiveMigration()
            .build()
}