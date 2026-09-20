package com.aspencarsi.bookroomdemo

import android.util.Log
import com.aspencarsi.bookroomdemo.data.local.dao.BookDao
import com.aspencarsi.bookroomdemo.data.network.api.BookService
import com.aspencarsi.bookroomdemo.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(private val bookDao: BookDao, private val bookService: BookService) {

    suspend fun getAllBooks():Flow<List<Book>>{
        try {
            val booksApis = bookService.getAllBooks()

            bookDao.clear()
            bookDao.insertAll(booksApis.map { it.toDatabase() })
        }catch (ex : Exception){
            Log.d("Error", ex.message.orEmpty())
        }



        return bookDao.getAllObservable().map {//se mapea dos veces porque el primero transforma el flow y el segundo los elementos de la lista
            list -> list.map { it.toDomain() }
        }
    }

}