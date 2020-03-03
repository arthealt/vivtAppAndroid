package com.fixee.vivt.application.intent

import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.data.remote.models.Notification

sealed class StateNotifications {
    class NormalState: StateNotifications()
    class LoadingState: StateNotifications()
    class SuccessLoad(val notifications: ArrayList<Notification>): StateNotifications()
    class ErrorLoad(val error: Error): StateNotifications()
}