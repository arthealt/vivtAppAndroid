package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fixee.vivt.data.storage.entity.Token
import com.fixee.vivt.domain.implementations.MainRepositoryImpl
import javax.inject.Inject

class BrsViewModelFactory @Inject constructor(private val token: Token, private val repository: MainRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BrsViewModel(token, repository) as T
    }
}