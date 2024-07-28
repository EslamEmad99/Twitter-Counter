package com.halan.data.di

import com.halan.data.data_source.remote.MainDataSource
import com.halan.data.repository.remote.MainRepositoryImpl
import com.halan.domain.repository.remote.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun providesMainRepository(mainDataSource: MainDataSource): MainRepository =
        MainRepositoryImpl(mainDataSource)
}