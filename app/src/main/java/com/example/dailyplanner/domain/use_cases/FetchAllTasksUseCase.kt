package com.example.dailyplanner.domain.use_cases

import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.repository.TaskRepositoryDatabase
import com.example.dailyplanner.domain.repository.TaskRepositoryNetwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllTasksUseCase @Inject constructor(
    val taskRepositoryNetwork: TaskRepositoryNetwork,
    val taskRepositoryDatabase: TaskRepositoryDatabase
){
    suspend fun fetchAllTasksFromNetwork(): List<Task> {
        return taskRepositoryNetwork.fetchAllTasks()
    }

    fun fetchAllTasksFromDatabase(): Flow<List<Task>> {
        return taskRepositoryDatabase.getAllTasks()
    }
}