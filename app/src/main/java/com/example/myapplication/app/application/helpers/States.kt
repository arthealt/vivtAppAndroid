package com.example.myapplication.app.application.helpers

import com.example.myapplication.app.data.remote.models.Auth

sealed class State {
    class LoadingState: State()
    class LoadedState<T>(val data: List<T>): State()
    class NoItemState: State()
    class ErrorState(val message: String?): State()

    class SuccessLogin(val auth: Auth): State()
    class ErrorLogin(val error: String): State()
}