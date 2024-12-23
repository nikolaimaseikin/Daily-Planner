package com.example.dailyplanner.domain.use_cases

import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.repository.TaskRepositoryDatabase
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    val taskRepositoryDatabase: TaskRepositoryDatabase
) {
    suspend fun deleteTask(task: Task) {
        taskRepositoryDatabase.deleteTask(task)
    }
}