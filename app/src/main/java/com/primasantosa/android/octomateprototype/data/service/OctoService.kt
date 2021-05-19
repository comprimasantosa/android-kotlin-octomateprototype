package com.primasantosa.android.octomateprototype.data.service

import com.primasantosa.android.octomateprototype.data.model.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface OctoService {
    @POST("login/email")
    fun postLogin(
        @Body body: Map<String, String>
    ): Deferred<List<LoginResponse>>
}