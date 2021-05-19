package com.primasantosa.android.octomateprototype.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.primasantosa.android.octomateprototype.BuildConfig
import com.primasantosa.android.octomateprototype.data.OctoRepository
import com.primasantosa.android.octomateprototype.data.service.OctoInterceptor
import com.primasantosa.android.octomateprototype.data.service.OctoService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Providers {
    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL

        fun provideWeatherService(): OctoService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val okhttp = OkHttpClient().newBuilder()
                .addInterceptor(OctoInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .client(okhttp)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(OctoService::class.java)
        }

        fun provideWeatherRepository(service: OctoService): OctoRepository {
            return OctoRepository(service)
        }
    }
}