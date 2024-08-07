package com.halan.data.data_source.local

import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    suspend fun getValue(key: String, default: Any?): Flow<Any?>
    suspend fun setValue(key: String, value: Any?)
}