package com.example.dailyplanner.ui.screen.task_list

import com.example.dailyplanner.domain.repository.Task
import kotlinx.coroutines.flow.Flow

data class TaskListState(
    val selectedDate: Long? = null,
    val localTaskList: List<Task> = listOf(),
    val networkTaskList: List<Task> = listOf(),
    val dataSource: DataSource = DataSource.LOCAL,
    val isLoaded: Boolean = false,
)
