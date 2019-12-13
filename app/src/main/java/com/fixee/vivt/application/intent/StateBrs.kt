package com.fixee.vivt.application.intent

import com.fixee.vivt.data.remote.models.Brs
import com.fixee.vivt.data.remote.models.Error

sealed class StateBrs {
    class NormalState: StateBrs()
    class LoadingState(val semester: Int): StateBrs()
    class SuccessLoad(val brs: ArrayList<Brs>, val semester: Int): StateBrs()
    class ErrorLoad(val error: Error, val semester: Int): StateBrs()
}