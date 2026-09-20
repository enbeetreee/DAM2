package com.aspencarsi.dependencyinversiondemo.domain

interface MainRepository {

    fun getForecast() : String
}