package com.fixee.vivt.di.component

import com.fixee.vivt.data.storage.RoomAppDatabase
import com.fixee.vivt.di.module.AppModule
import com.fixee.vivt.di.module.NetworkModule
import com.fixee.vivt.di.module.StorageModule
import com.fixee.vivt.di.scope.PerApplication
import dagger.Component

@PerApplication
@Component(modules = [AppModule::class, StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun provideRoomAppDatabase(): RoomAppDatabase

    fun plusLoginComponent(): LoginComponent

    fun plusMainComponent(): MainComponent

}