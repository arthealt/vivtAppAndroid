package com.fixee.vivt.application.intent

import com.fixee.vivt.data.remote.models.Error
import com.fixee.vivt.data.remote.models.Teacher

sealed class StateTeachers {
    class NormalState: StateTeachers()
    class LoadingState(): StateTeachers()
    class SuccessLoad(val teachers: ArrayList<Teacher>): StateTeachers()
    class ErrorLoad(val error: Error): StateTeachers()
}