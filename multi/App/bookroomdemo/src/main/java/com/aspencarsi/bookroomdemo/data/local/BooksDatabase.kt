package com.aspencarsi.bookroomdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aspencarsi.bookroomdemo.data.local.dao.BookDao
import com.aspencarsi.bookroomdemo.data.local.entities.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BooksDatabase : RoomDatabase(){
    abstract fun bookDao() : BookDao
}