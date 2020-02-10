package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.*
import com.fixee.vivt.application.intent.StateSettings
import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.domain.implementations.SettingsRepositoryImpl
import kotlinx.coroutines.*

class SettingsViewModel(private val repository: SettingsRepositoryImpl): ViewModel(), LifecycleObserver {

    val state: MutableLiveData<StateSettings> = MutableLiveData<StateSettings>().apply { value = StateSettings.NormalState() }

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun updatePush(field: Int, valueField: Boolean) {
        
        state.apply { value = StateSettings.LoadingState() }

        viewModelScope.launch {
            try {
                val status = repository.updatePushChange(field, valueField).await()

                launch(Dispatchers.Main) {
                    @Suppress("UNCHECKED_CAST")
                    state.apply {
                        value = when (status.status) {
                            "success" -> StateSettings.SuccessUpdate()
                            else -> StateSettings.ErrorUpdate(status.error[0])
                        }
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    state.apply { value = StateSettings.ErrorUpdate(Error(299, e.localizedMessage)) }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewModelJob.cancelChildren()
    }
}