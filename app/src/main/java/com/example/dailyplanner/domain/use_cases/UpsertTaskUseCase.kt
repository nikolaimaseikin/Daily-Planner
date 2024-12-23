package com.example.dailyplanner.domain.use_cases

import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.repository.TaskRepositoryDatabase
import javax.inject.Inject

class UpsertTaskUseCase @Inject constructor(
    val taskRepositoryDatabase: TaskRepositoryDatabase
) {
    suspend fun upsertTask(task: Task) {
        taskRepositoryDatabase.upsertTask(task)
    }
}