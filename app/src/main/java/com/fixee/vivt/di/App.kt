package com.fixee.vivt.di

import android.app.Application
import com.fixee.vivt.di.component.AppComponent
import com.fixee.vivt.di.component.DaggerAppComponent
import com.fixee.vivt.di.component.LoginComponent
import com.fixee.vivt.di.component.MainComponent
import com.fixee.vivt.di.module.AppModule

class App: Application() {

    companion object{

        private lateinit var component: AppComponent
        private var loginComponent: LoginComponent? = null
        private var mainComponent: MainComponent? = null

        fun getComponent(): AppComponent {
            return component
        }

        fun getLoginComponent(): LoginComponent {
            if (loginComponent == null) {
                loginComponent = component.plusLoginComponent()
            }
            return loginComponent as LoginComponent
        }

        fun clearLoginComponent() {
            loginComponent = null
        }

        fun getMainComponent(): MainComponent {
            if (mainComponent == null) {
                mainComponent = component.plusMainComponent()
            }
            return mainComponent as MainComponent
        }

        fun clearMainComponent() {
            mainComponent = null
        }
    }


    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}