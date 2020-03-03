package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.*
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.application.intent.StateTeachers
import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.domain.implementations.MainRepositoryImpl
import kotlinx.coroutines.*

class TeachersViewModel (private val util: Util, private val repository: MainRepositoryImpl): ViewModel(), LifecycleObserver {

    val state: MutableLiveData<StateTeachers> = MutableLiveData<StateTeachers>().apply { value = StateTeachers.NormalState() }

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        getTeachers()
    }

    fun getTeachers() {
        if (util.isInternetConnection()) {
            state.apply { StateTeachers.LoadingState() }

            viewModelScope.launch {
                try {
                    val teachers = repository.getTeachers()

                    launch(Dispatchers.Main) {
                        @Suppress("UNCHECKED_CAST")
                        state.apply {
                            value = when (teachers.status) {
                                "success" -> StateTeachers.SuccessLoad(teachers.list!!)
                                else ->  StateTeachers.ErrorLoad(teachers.error[0])
                            }
                        }
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                        state.apply { value = StateTeachers.ErrorLoad(Error(299, e.localizedMessage)) }
                    }
                }
            }
        } else {
            state.apply { value = StateTeachers.ErrorLoad(Error(298, "")) }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewModelJob.cancelChildren()
    }
}