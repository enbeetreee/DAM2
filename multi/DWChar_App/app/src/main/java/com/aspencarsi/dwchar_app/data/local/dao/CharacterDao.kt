package com.aspencarsi.dwchar_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.aspencarsi.dwchar_app.data.local.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Characters")
    suspend fun getAll() : List<CharacterEntity>

    @Query("SELECT * FROM Characters c WHERE c.name == :name")
    suspend fun getOne(name: String) : CharacterEntity

//    @Query("SELECT * FROM Characters")
//    fun getAllPaged() : PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(characters:List<CharacterEntity>)

//
//    @Update
//     suspend fun update(vararg characters: CharacterEntity)
//
//    @Delete
//     suspend fun delete(vararg characters: CharacterEntity)
//


    @Query("DELETE FROM Characters")
   suspend fun clearAll()

    @Upsert
   suspend fun upsertAll(characters: List<CharacterEntity>)

}