package com.example.dailyplanner.domain.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    var name: String,
    @SerialName("date_start") var startDate: Long,
    @SerialName("date_finish") var endDate: Long,
    var description: String,
    @SerialName("is_completed") var isCompleted: Boolean,
)
