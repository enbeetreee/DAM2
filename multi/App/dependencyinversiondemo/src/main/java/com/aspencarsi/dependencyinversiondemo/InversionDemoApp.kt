package com.aspencarsi.dependencyinversiondemo

import android.app.Application
import com.aspencarsi.dependencyinversiondemo.domain.MainRepository
import com.aspencarsi.dependencyinversiondemo.providers.RepositoryProvider
import com.aspencarsi.dependencyinversiondemo.providers.SoleadoRepositoryProvider

//tocado Android Manifest con name
class InversionDemoApp : Application() {

    val repositoryProvider: RepositoryProvider = SoleadoRepositoryProvider()
    val repository: MainRepository = repositoryProvider.getRepository()


}