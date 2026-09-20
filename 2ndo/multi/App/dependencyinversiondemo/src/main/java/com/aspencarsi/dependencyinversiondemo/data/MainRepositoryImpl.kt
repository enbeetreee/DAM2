package com.aspencarsi.dependencyinversiondemo.data

import com.aspencarsi.dependencyinversiondemo.domain.MainRepository

class MainRepositoryImpl : MainRepository{
    override fun getForecast() : String = "Soleado"
}