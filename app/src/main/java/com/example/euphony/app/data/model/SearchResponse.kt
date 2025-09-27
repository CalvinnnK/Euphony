package com.example.euphony.app.data.model

import com.google.gson.annotations.SerializedName


data class SearchResponse<T>(
    @SerializedName("resultCount") val resultCount: Int?,
    @SerializedName("results") val results: List<T>?,
)
