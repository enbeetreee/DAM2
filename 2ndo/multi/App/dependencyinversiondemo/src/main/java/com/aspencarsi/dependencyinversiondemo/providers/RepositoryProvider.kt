package com.aspencarsi.dependencyinversiondemo.providers

import com.aspencarsi.dependencyinversiondemo.domain.MainRepository

interface RepositoryProvider {
    fun getRepository(): MainRepository
}