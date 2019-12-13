package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.domain.implementations.MainRepositoryImpl
import kotlinx.coroutines.*

class MainViewModel(private val token: Token, private val repository: MainRepositoryImpl): ViewModel(), LifecycleObserver {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout(token.token)
            } catch (e: Exception) {}
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewModelJob.cancelChildren()
    }
}