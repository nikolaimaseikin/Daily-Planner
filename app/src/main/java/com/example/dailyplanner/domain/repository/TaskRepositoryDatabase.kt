package com.example.dailyplanner.domain.repository

import com.example.dailyplanner.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepositoryDatabase {
    suspend fun upsertTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(id: Int): Task
}