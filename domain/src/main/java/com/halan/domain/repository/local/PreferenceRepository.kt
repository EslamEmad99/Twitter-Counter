package com.halan.domain.repository.local

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun getLanguage(): Flow<String>
    suspend fun setLanguage(lang: String)
    suspend fun getToken(): Flow<String>
    suspend fun setToken(token: String)
}