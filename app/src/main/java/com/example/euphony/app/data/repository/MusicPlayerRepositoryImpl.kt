package com.example.euphony.app.data.repository

import com.example.euphony.app.data.model.MusicResponse
import com.example.euphony.app.domain.repository.MusicPlayerRepository
import com.example.euphony.app.data.remote.ApiMusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MusicPlayerRepositoryImpl @Inject constructor(
    private val apiMusicService: ApiMusicService
): MusicPlayerRepository {
    override fun getMusicItems(term: String, limit: Int): Flow<List<MusicResponse>> = flow {
        try {
            val response = apiMusicService.searchMusic(term = term)
            emit(response.results ?: emptyList())
        } catch (_: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}