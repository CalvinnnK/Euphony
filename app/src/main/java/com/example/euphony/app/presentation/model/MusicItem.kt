package com.example.euphony.app.presentation.model

import com.example.euphony.app.data.model.MusicResponse

data class MusicItem(
    val musicId: Long,
    val musicName: String,
    val artistName: String,
    val primaryGenreName: String,
    val previewUrl: String,
    val artwork30Url: String,
    val artwork60Url: String,
    val collectionViewUrl: String,
) {
    companion object {
        fun parseToMusicItem(musicResponse: MusicResponse): MusicItem {
            return MusicItem(
                musicId = musicResponse.trackId ?: 0,
                musicName = musicResponse.trackName ?: "",
                artistName = musicResponse.artistName ?: "",
                primaryGenreName = musicResponse.primaryGenreName ?: "",
                previewUrl = musicResponse.previewUrl ?: "",
                artwork30Url = musicResponse.artworkUrl30 ?: "",
                artwork60Url = musicResponse.artworkUrl60 ?: "",
                collectionViewUrl = musicResponse.trackName ?: ""
            )
        }
    }
}

