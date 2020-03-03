package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.*
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.intent.StateNotifications
import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.domain.implementations.MainRepositoryImpl
import kotlinx.coroutines.*

class NotificationsViewModel(private val util: Util, private val repository: MainRepositoryImpl): ViewModel(), LifecycleObserver {

    val state: MutableLiveData<StateNotifications> = MutableLiveData<StateNotifications>().apply { value = StateNotifications.NormalState() }

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        getNotifications()
    }

    fun getNotifications() {

        if (util.isInternetConnection()) {
            state.apply { value = StateNotifications.LoadingState() }

            viewModelScope.launch {
                try {
                    val notifications = repository.getNotifications()

                    launch(Dispatchers.Main) {
                        @Suppress("UNCHECKED_CAST")
                        state.apply {
                            value = when {
                                notifications.notification == null -> StateNotifications.ErrorLoad(
                                    Error(301, "")
                                )
                                notifications.status == "success" -> StateNotifications.SuccessLoad(
                                    notifications.notification
                                )
                                else -> StateNotifications.ErrorLoad(notifications.error[0])
                            }
                        }
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                        state.apply {
                            value = StateNotifications.ErrorLoad(Error(299, e.localizedMessage))
                        }
                    }
                }
            }
        } else {
            state.apply { value = StateNotifications.ErrorLoad(Error(298, "")) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout()
            } catch (e: Exception) {}
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewModelJob.cancelChildren()
    }
}