package com.primasantosa.android.octomateprototype.data.model

import java.util.*

data class Announcement(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val subtitle: String,
    val body: String,
    val date: String,
    val tag: String
)