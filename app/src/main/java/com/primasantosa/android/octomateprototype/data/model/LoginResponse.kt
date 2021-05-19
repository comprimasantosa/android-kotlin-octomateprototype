package com.primasantosa.android.octomateprototype.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "associateData")
    val associateData: LoginDataResponse?
)

@JsonClass(generateAdapter = true)
data class LoginDataResponse(
    @Json(name = "name")
    val name: String?
) {
    fun toLoginData(): LoginData {
        return LoginData(name = name ?: "Unknown")
    }
}