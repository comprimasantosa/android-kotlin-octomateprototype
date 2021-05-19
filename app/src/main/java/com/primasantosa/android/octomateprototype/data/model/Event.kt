package com.primasantosa.android.octomateprototype.data.model

import java.util.*

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val timeStart: String,
    val timeEnd: String,
    val totalHours: String,
    val totalBreak: String,
    val isEditable: Boolean,
    val tag: String
)