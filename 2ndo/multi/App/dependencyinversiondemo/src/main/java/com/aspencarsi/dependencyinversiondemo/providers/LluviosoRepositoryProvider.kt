package com.aspencarsi.dependencyinversiondemo.providers

import com.aspencarsi.dependencyinversiondemo.data.MainRepositoryImpl2
import com.aspencarsi.dependencyinversiondemo.domain.MainRepository

class LluviosoRepositoryProvider : RepositoryProvider {
    override fun getRepository(): MainRepository = MainRepositoryImpl2()
}