package com.fixee.vivt.application.intent

import com.fixee.vivt.data.remote.models.Error

sealed class StateSettings {
    class NormalState: StateSettings()
    class LoadingState: StateSettings()
    class SuccessUpdate: StateSettings()
    class ErrorUpdate(val error: Error): StateSettings()
}