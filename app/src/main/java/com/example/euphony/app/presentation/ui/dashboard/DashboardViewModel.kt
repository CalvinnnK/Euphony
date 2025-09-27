package com.example.euphony.app.presentation.ui.dashboard

import com.example.euphony.app.presentation.ui.base.BaseViewModel
import com.example.euphony.app.domain.repository.MusicPlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MusicPlayerRepository
) : BaseViewModel() {

}