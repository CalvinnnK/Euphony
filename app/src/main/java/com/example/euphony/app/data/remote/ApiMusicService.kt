package com.example.euphony.app.data.remote

import com.example.euphony.app.data.model.MusicResponse
import com.example.euphony.app.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMusicService {

    @GET("search")
    suspend fun searchMusic(
        @Query("term") term: String = "",
        @Query("limit") limit: Int = 20,
        @Query("entity") entity: String = "song",
    ): SearchResponse<MusicResponse>

}