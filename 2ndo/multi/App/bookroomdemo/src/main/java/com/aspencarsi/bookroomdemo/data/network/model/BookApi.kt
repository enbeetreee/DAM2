package com.aspencarsi.bookroomdemo.data.network.model

data class BookApi(
    val title: String,
    val length: Int,
    val cover : String? = null
)
