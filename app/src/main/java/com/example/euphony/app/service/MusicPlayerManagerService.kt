package com.example.euphony.app.service

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata.Builder
import androidx.media3.common.Player
import com.example.euphony.app.presentation.model.MusicItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerManagerService @Inject constructor(
    private val player: Player
){
    fun getMusicPlayer() : Player{
        return this.player
    }

    fun playMusic(){
        player.play()
    }

    fun pauseMusic(){
        player.pause()
    }

    fun setMusicList(musicList: List<MusicItem>, autoStart: Boolean = true){
        val mediaItems = musicList.map { song ->
            val metadata = Builder()
                .setTitle(song.musicName)
                .setArtist(song.artistName)
                .setArtworkUri(song.artwork60Url.toUri())
                .build()

            MediaItem.Builder()
                .setUri(song.previewUrl)
                .setMediaId(song.musicId.toString())
                .setMediaMetadata(metadata)
                .build()
        }

        player.setMediaItems(mediaItems)
        player.prepare()

        if (autoStart) {
            player.play()
        }
    }

    fun skipToNext() {
        player.seekToNextMediaItem()
    }
    fun skipToPrevious() {
        player.seekToPreviousMediaItem()
    }

    fun isPlaying(): Boolean{
        return player.isPlaying
    }

    fun getPlayerState(): Int{
        return player.playbackState
    }

    fun getPlayerPositionTime(): Long{
        return player.currentPosition
    }

    fun setPositionPlayer(position: Long){
        player.seekTo(position)
    }

    fun getPlayerDuration(): Long{
        return player.duration
    }

    fun setToDefaultPosition(index: Int){
        player.seekToDefaultPosition(index)
    }

    fun getCurrentMediaItemIndex(): Int{
        return player.currentMediaItemIndex
    }
}