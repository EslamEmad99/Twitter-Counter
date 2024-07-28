package com.halan.data.di

import android.content.Context
import com.halan.data.data_source.remote.MainDataSource
import com.halan.data.remote.data_source.MainRemoteDataSourceImpl
import com.halan.data.remote.end_points.MainEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideMainDataSource(mainEndPoints: MainEndPoints): MainDataSource =
        MainRemoteDataSourceImpl(mainEndPoints)
}