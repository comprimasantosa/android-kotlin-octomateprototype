package com.primasantosa.android.octomateprototype.data

import com.primasantosa.android.octomateprototype.data.model.LoginData
import com.primasantosa.android.octomateprototype.data.service.OctoService

class OctoRepository(private val service: OctoService) {
    suspend fun postLogin(body: Map<String, String>): LoginData {
        val response = service.postLogin(body).await()
        return response[0].associateData?.toLoginData() ?: LoginData(name = "Unknown")
    }
}