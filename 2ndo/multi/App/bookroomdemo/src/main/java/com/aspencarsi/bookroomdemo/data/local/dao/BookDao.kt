package com.aspencarsi.bookroomdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.aspencarsi.bookroomdemo.data.local.entities.BookEntity
import com.aspencarsi.bookroomdemo.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class BookDao {

    @Query("SELECT * FROM Books")
    abstract fun getAll() : Flow<List<BookEntity>>

    @Query("SELECT * FROM Books")
    abstract fun getAllObservable() : Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(books:List<BookEntity>)

    @Query("SELECT * FROM Books WHERE id = :bookId")
    protected abstract fun getBookByIdCore(bookId : Long) : Flow<BookEntity>


    suspend fun getBookById(bookId : Long) = getBookByIdCore(bookId).distinctUntilChanged()

    @Query("DELETE FROM books")
    abstract suspend fun clearAll()

    @Upsert
    abstract suspend fun upsertAll(books: List<BookEntity>)

}