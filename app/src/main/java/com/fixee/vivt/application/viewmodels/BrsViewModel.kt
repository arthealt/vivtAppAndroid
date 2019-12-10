package com.fixee.vivt.application.viewmodels

import android.os.Parcelable
import androidx.lifecycle.*
import com.fixee.vivt.application.helpers.StateBrs
import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.domain.implementations.MainRepositoryImpl
import kotlinx.coroutines.*

class BrsViewModel (private val token: Token, private val repository: MainRepositoryImpl): ViewModel(), LifecycleObserver {

    var view: Parcelable? = null

    val state: MutableLiveData<StateBrs> = MutableLiveData<StateBrs>().apply { value = StateBrs.NormalState() }

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        getBrs(1)
    }

    fun getBrs(semester: Int) {
        state.apply { value = StateBrs.LoadingState(semester) }
        viewModelScope.launch {
            try {
                val responseBrs = repository.getBrs(token.token, semester).await()

                launch(Dispatchers.Main) {
                    @Suppress("UNCHECKED_CAST")
                    state.apply {
                        value = when {
                            responseBrs.brs == null -> StateBrs.ErrorLoad(Error(301, ""), semester)
                            responseBrs.status == "success" -> StateBrs.SuccessLoad(responseBrs.brs, semester)
                            else -> StateBrs.ErrorLoad(responseBrs.error[0], semester)
                        }
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    state.apply { value = StateBrs.ErrorLoad(Error(299, e.localizedMessage), semester) }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewModelJob.cancelChildren()
    }

}