package com.halan.data.repository.local

import com.halan.data.data_source.local.PreferenceDataSource
import com.halan.data.util.Languages.ARABIC
import com.halan.data.util.PreferenceConstants.LANGUAGE
import com.halan.data.util.PreferenceConstants.TOKEN
import com.halan.domain.repository.local.PreferenceRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(private val preferenceDataSource: PreferenceDataSource) :
    PreferenceRepository {

    override suspend fun getLanguage() = flow {
        preferenceDataSource.getValue(LANGUAGE, ARABIC).collect {
            emit(it as String)
        }
    }

    override suspend fun setLanguage(lang: String) {
        return preferenceDataSource.setValue(LANGUAGE, lang)
    }

    override suspend fun getToken() = flow {
        preferenceDataSource.getValue(TOKEN, "").collect {
            emit(it as String)
        }
    }

    override suspend fun setToken(token: String) {
        preferenceDataSource.setValue(TOKEN, token)
    }
}