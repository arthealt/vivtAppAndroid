package com.example.myapplication.app.application.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.myapplication.app.application.helpers.State
import com.example.myapplication.app.data.storage.entity.People
import com.example.myapplication.app.domain.implementations.TestRepositoryImpl
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: TestRepositoryImpl): ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData<State>().apply { value = State.LoadingState() }

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun auth(email: String, password: String) {
        viewModelScope.launch {
            try {
                val auth = repository.auth(email, password).await()

                launch(Dispatchers.Main) {
                    state.apply { value = if (auth.status == "success") State.SuccessLogin(auth) else State.ErrorLogin(auth.error[0].name) }
                }
            } catch (e: Exception) {
                state.apply { value = State.ErrorLogin("Error!") }
            }
        }
    }

    fun uploadLocal(people: People) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.uploadLocal(people)
        }
    }

    fun fetchLocal() {
        viewModelScope.launch {
            try{
                val peoples = repository.fetchLocal().await()

                launch(Dispatchers.Main) {
                    if (peoples.isEmpty()) {
                        state.apply { value = State.NoItemState() }
                    } else {
                        state.apply { value = State.LoadedState(peoples) }
                    }
                }

            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    state.apply { value = State.ErrorState(e.localizedMessage) }
                }
            }

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelChildrenJob() {
        println(">>>>>>>>>>cancel<<<<<<<<<<<<")
        viewModelJob.cancelChildren()
    }

}