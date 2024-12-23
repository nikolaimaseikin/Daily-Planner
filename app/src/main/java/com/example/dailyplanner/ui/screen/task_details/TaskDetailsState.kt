package com.example.dailyplanner.ui.screen.task_details

import com.example.dailyplanner.domain.repository.Task

data class TaskDetailsState(
    val selectedTask: Task? = null,
)
