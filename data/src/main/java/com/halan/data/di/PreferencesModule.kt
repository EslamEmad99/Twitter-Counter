package com.halan.data.di

import android.content.Context
import com.halan.data.data_source.local.PreferenceDataSource
import com.halan.data.local.PreferenceDataSourceImpl
import com.halan.data.repository.local.PreferenceRepositoryImpl
import com.halan.domain.repository.local.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun providePreferencesDataSource(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSourceImpl(context)

    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesDataSource: PreferenceDataSource): PreferenceRepository =
        PreferenceRepositoryImpl(preferencesDataSource)
}