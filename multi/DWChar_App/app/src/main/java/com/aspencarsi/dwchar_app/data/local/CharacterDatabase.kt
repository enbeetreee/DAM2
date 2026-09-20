package com.aspencarsi.dwchar_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aspencarsi.dwchar_app.data.local.dao.CharacterDao
import com.aspencarsi.dwchar_app.data.local.entities.CharacterEntity


@Database(
    entities = [
        CharacterEntity::class,
        //SpecieEntity::class,
        /*LocationEntity::class*/], version = 1
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
    //abstract val locationDao: LocationDao
    //abstract val specieDao: SpecieDao
}