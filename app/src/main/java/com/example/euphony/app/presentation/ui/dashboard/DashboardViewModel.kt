package com.example.euphony.app.presentation.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.euphony.app.data.model.MusicResponse
import com.example.euphony.app.presentation.ui.base.BaseViewModel
import com.example.euphony.app.domain.repository.MusicPlayerRepository
import com.example.euphony.app.presentation.model.MusicItem
import com.example.euphony.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MusicPlayerRepository
) : BaseViewModel() {

    private val _musicItems = MutableLiveData<Event<List<MusicItem>>>()
    val musicItems: LiveData<Event<List<MusicItem>>> = _musicItems

    fun searchMusic(query: String) {
        viewModelScope.launch {
            repository.getMusicItems(query)
                .onStart {
                    _loading.postValue(Event(true))
                }
                .map { responseList ->
                    // Now, map each item in the list to your MusicItem data class
                    responseList.map { musicResponse ->
                        MusicItem.parseToMusicItem(musicResponse)
                    }
                }
                .collect { musicList ->
                    _loading.postValue(Event(false))
                    _musicItems.postValue(Event(musicList))
                }

        }
    }
}