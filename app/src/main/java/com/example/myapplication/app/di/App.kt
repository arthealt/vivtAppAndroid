package com.example.myapplication.app.di

import android.app.Application
import com.example.myapplication.app.di.component.Component
import com.example.myapplication.app.di.component.DaggerComponent
import com.example.myapplication.app.di.module.ContextModule

class App: Application() {

    companion object{
        private lateinit var component: Component

        fun getComponent(): Component {
            return component
        }
    }


    override fun onCreate() {
        super.onCreate()
        component = DaggerComponent.builder().contextModule(ContextModule(this)).build()
    }
}