package com.aspencarsi.dependencyinversiondemo.providers

import com.aspencarsi.dependencyinversiondemo.data.MainRepositoryImpl
import com.aspencarsi.dependencyinversiondemo.domain.MainRepository

class SoleadoRepositoryProvider : RepositoryProvider {
    override fun getRepository(): MainRepository = MainRepositoryImpl()
}