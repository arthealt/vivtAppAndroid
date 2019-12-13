package com.fixee.vivt.application.intent

sealed class StateLogin {
    class NormalState: StateLogin()
    class LoadingState: StateLogin()
    class SuccessLogin: StateLogin()
    class ErrorLogin(val error: String): StateLogin()
}