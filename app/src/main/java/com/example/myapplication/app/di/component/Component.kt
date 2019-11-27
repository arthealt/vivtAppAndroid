package com.example.myapplication.app.di.component

import com.example.myapplication.app.application.activity.MainActivity
import com.example.myapplication.app.di.module.ContextModule
import com.example.myapplication.app.di.module.NetworkModule
import com.example.myapplication.app.di.module.RepositoryModule
import com.example.myapplication.app.di.module.StorageModule
import com.example.myapplication.app.di.scope.PerApplication
import dagger.Component

@PerApplication
@Component(modules = [ContextModule::class, StorageModule::class, NetworkModule::class, RepositoryModule::class])
interface Component {

    fun inject(mainActivity: MainActivity)

}