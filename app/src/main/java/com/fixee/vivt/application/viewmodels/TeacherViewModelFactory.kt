package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fixee.vivt.application.helpers.Util
import com.fixee.vivt.data.storage.entity.User
import com.fixee.vivt.domain.implementations.MainRepositoryImpl
import javax.inject.Inject

class TeacherViewModelFactory @Inject constructor(private val util: Util, private val user: User, private val repository: MainRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TeachersViewModel(util, user, repository) as T
    }
}