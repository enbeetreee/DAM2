package com.aspencarsi.bookroomdemo.data.network.api

import com.aspencarsi.bookroomdemo.data.network.model.BookApi

interface BookService {
    suspend fun getAllBooks(): List<BookApi>
}