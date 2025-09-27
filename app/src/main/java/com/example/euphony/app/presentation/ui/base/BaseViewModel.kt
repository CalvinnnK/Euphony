package com.example.euphony.app.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.euphony.core.utils.Event

abstract class BaseViewModel : ViewModel(){

    protected val _loading = MutableLiveData<Event<Boolean>>()
    val loading : LiveData<Event<Boolean>> get() = _loading


}