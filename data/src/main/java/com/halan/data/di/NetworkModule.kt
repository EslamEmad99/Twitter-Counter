package com.halan.data.di

import androidx.appcompat.app.AppCompatDelegate
import com.halan.data.util.Languages.ARABIC
import com.halan.data.util.NetworkConstants
import com.halan.data.util.NetworkConstants.AUTHORIZATION
import com.halan.data.util.NetworkConstants.LANGUAGE
import com.halan.data.util.NetworkConstants.NETWORK_TIMEOUT
import com.halan.domain.repository.local.PreferenceRepository
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.toccan.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun moshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .log(Platform.WARN)
            .request("Request ->")
            .response("Response ->")
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: LoggingInterceptor,
        preferenceRepository: PreferenceRepository
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .header(LANGUAGE, getLanguage(preferenceRepository).ifEmpty {
                    Locale.getDefault().language
                }).header(
                    AUTHORIZATION, getToken(preferenceRepository)
                )

            val request = requestBuilder.build()
            return@addInterceptor chain.proceed(request)
        }.connectTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS).build()
    }

    private fun getLanguage(preferenceRepository: PreferenceRepository) =
        AppCompatDelegate.getApplicationLocales().get(0)?.language ?: ARABIC

    private fun getToken(preferenceRepository: PreferenceRepository): String {
        var token: String
        runBlocking(Dispatchers.IO) {
            token = preferenceRepository.getToken().first()
        }
        return if (token.isNotEmpty()) {
            "${NetworkConstants.BEARER}$token"
        } else {
            ""
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL).build()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO
}
