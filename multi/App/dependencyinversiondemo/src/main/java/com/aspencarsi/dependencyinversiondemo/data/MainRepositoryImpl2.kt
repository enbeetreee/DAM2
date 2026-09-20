package com.aspencarsi.dependencyinversiondemo.data

import com.aspencarsi.dependencyinversiondemo.domain.MainRepository

class MainRepositoryImpl2 : MainRepository {
    override fun getForecast() : String = "Lluvias Torrenciales"
}