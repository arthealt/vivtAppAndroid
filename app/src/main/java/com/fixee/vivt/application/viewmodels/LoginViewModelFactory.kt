package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fixee.vivt.domain.implementations.LoginRepositoryImpl
import javax.inject.Inject

class LoginViewModelFactory  @Inject constructor(private val repository: LoginRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LoginViewModel(repository) as T
    }
}