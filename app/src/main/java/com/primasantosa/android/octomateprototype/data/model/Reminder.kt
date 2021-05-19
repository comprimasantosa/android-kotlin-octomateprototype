package com.primasantosa.android.octomateprototype.data.model

import java.util.*

data class Reminder(
    val id : String = UUID.randomUUID().toString(),
    val title: String,
    val placement: String,
    val dateFrom: String,
    val dateTo: String
)