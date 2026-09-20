package com.aspencarsi.dwchar_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.aspencarsi.dwchar_app.data.local.entities.CharacterEntity
import com.aspencarsi.dwchar_app.data.local.entities.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM Locations")
    suspend fun getAll() : List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(characters:List<LocationEntity>)

//
//    @Update
//     suspend fun update(vararg characters: CharacterEntity)
//
//    @Delete
//     suspend fun delete(vararg characters: CharacterEntity)
//


    @Query("DELETE FROM Locations")
   suspend fun clearAll()

    @Upsert
   suspend fun upsertAll(characters: List<LocationEntity>)

}