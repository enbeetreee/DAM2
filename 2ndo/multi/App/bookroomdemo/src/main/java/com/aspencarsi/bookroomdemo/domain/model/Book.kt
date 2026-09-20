package com.aspencarsi.bookroomdemo.domain.model

data class Book (
    val title: String,
    val length: Int,
    val cover : String? = null
)