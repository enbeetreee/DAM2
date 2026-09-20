package com.aspencarsi.bookroomdemo

import com.aspencarsi.bookroomdemo.data.local.entities.BookEntity
import com.aspencarsi.bookroomdemo.data.network.model.BookApi
import com.aspencarsi.bookroomdemo.domain.model.Book

fun BookEntity.toDomain() : Book =//funcion extendida
    Book(this.title, this.length, this.cover.orEmpty())

fun BookApi.toDomain() : Book = Book(this.title, this.length, this.cover.orEmpty())

fun BookApi.toDatabase() : BookEntity =
    BookEntity(title = this.title, length = this.length, cover =  this.cover.orEmpty())