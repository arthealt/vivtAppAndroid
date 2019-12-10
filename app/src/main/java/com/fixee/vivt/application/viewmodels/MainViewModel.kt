package com.fixee.vivt.application.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val backStack: MutableLiveData<ArrayList<Int>> = MutableLiveData<ArrayList<Int>>().apply { value = ArrayList() }

}