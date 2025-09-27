package com.example.euphony.app.domain.repository

import com.example.euphony.app.data.model.MusicResponse
import kotlinx.coroutines.flow.Flow

interface MusicPlayerRepository {
    fun getMusicItems(term: String): Flow<List<MusicResponse>>
}