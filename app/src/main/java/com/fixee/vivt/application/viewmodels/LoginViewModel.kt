package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.*
import com.fixee.vivt.application.helpers.StateLogin
import com.fixee.vivt.domain.implementations.LoginRepositoryImpl
import kotlinx.coroutines.*

class LoginViewModel (private val repository: LoginRepositoryImpl): ViewModel(), LifecycleObserver {

    val state: MutableLiveData<StateLogin> = MutableLiveData<StateLogin>().apply { value = StateLogin.NormalState() }

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun auth(email: String, password: String) {
        state.apply { value = StateLogin.LoadingState() }
        viewModelScope.launch {
            try {
                val auth = repository.auth(email, password).await()

                launch(Dispatchers.Main) {
                    state.apply { value = if (auth.status == "success") StateLogin.SuccessLogin(auth) else StateLogin.ErrorLogin(auth.error[0].name) }

                    if (auth.status == "success") pushToken(auth.token, auth.userStatus)
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    state.apply { value = StateLogin.ErrorLogin(e.localizedMessage) }
                }
            }
        }
    }

    private fun pushToken(token: String, userStatus: String) {
        viewModelScope.launch {
            repository.pushToken(token, userStatus)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewModelJob.cancelChildren()
    }

}