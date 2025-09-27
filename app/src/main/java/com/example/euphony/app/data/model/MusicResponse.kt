package com.example.euphony.app.data.model

import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("trackId") val trackId: Long?,
    @SerializedName("collectionId") val collectionId: Long?,
    @SerializedName("artistId") val artistId: Long?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("collectionName") val collectionName: String?,
    @SerializedName("trackName") val trackName: String?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("artworkUrl100") val artworkUrl100: String?,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("primaryGenreName") val primaryGenreName: String?,
)
