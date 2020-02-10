package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fixee.vivt.domain.implementations.SettingsRepositoryImpl
import javax.inject.Inject

class SettingsViewModelFactory @Inject constructor(private val settingsRepository: SettingsRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SettingsViewModel(settingsRepository) as T
    }
}