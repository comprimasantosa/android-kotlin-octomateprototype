package com.primasantosa.android.octomateprototype.data.service

import okhttp3.Interceptor
import okhttp3.Response

class OctoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}