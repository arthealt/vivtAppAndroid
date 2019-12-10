package com.fixee.vivt.application.helpers

import com.fixee.vivt.data.remote.models.Auth

sealed class StateLogin {
    class NormalState: StateLogin()
    class LoadingState: StateLogin()
    class SuccessLogin(val auth: Auth): StateLogin()
    class ErrorLogin(val error: String): StateLogin()
}