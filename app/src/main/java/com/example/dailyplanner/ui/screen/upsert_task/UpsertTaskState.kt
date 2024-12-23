package com.example.dailyplanner.ui.screen.upsert_task

import com.example.dailyplanner.domain.repository.Task

data class UpsertTaskState(
    val id: Int = 0,
    val name: String = "",
    val startDate: Long = 0,
    val endDate: Long = 0,
    val description: String = "",
    val isCompleted: Boolean = false,
    val isModified: Boolean = false,
)
