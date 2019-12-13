package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fixee.vivt.domain.implementations.SettingsRepositoryImpl
import javax.inject.Inject
import javax.inject.Named

class SettingsViewModelFactory @Inject constructor(@param:Named("fcmToken") private val token: String, private val settingsRepository: SettingsRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SettingsViewModel(token, settingsRepository) as T
    }
}