package com.example.euphony.app.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.euphony.app.presentation.ui.base.BaseViewModel
import com.example.euphony.app.domain.repository.MusicPlayerRepository
import com.example.euphony.app.presentation.model.MusicItem
import com.example.euphony.app.service.MusicPlayerManagerService
import com.example.euphony.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MusicPlayerRepository,
    private val musicPlayerService: MusicPlayerManagerService
) : BaseViewModel() {

    private val _musicItems = MutableLiveData<Event<List<MusicItem>>>()
    val musicItems: LiveData<Event<List<MusicItem>>> = _musicItems

    val player get() = musicPlayerService.getMusicPlayer()

    var isUserSeeking = false

    fun searchMusic(query: String) {
        viewModelScope.launch {
            repository.getMusicItems(query)
                .onStart {
                    _loading.postValue(Event(true))
                }
                .map { responseList ->
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

    fun setListMusic(musicList: List<MusicItem>, autoStart: Boolean = true){
        musicPlayerService.setMusicList(musicList, autoStart)
    }

    fun playPlayer(){
        musicPlayerService.playMusic()
    }

    fun pausePlayer(){
        musicPlayerService.pauseMusic()
    }

    fun skipToNextPlayer(){
        musicPlayerService.skipToNext()
    }

    fun skipToPreviousPlayer(){
        musicPlayerService.skipToPrevious()
    }

    fun isPlayerPlaying(): Boolean{
        return musicPlayerService.isPlaying()
    }

    fun setPositionPlayer(position: Long){
        musicPlayerService.setPositionPlayer(position)
    }

    fun getPlayerState():Int{
        return musicPlayerService.getPlayerState()
    }

    fun getPlayerPosition(): Long{
        return musicPlayerService.getPlayerPosition()
    }
    fun getPlayerDuration(): Long{
        return musicPlayerService.getPlayerDuration()
    }

    fun formatTime(currentPosition: Long): String {
        if (currentPosition < 0) return "00:00"

        val seconds = (currentPosition / 1000).toInt()
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}