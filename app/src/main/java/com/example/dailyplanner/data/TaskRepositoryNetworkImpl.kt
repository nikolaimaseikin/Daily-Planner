package com.example.dailyplanner.data

import com.example.dailyplanner.data.network.TaskApi
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.repository.TaskRepositoryNetwork
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TaskRepositoryNetworkImpl @Inject constructor(
    val taskNetworkApi: TaskApi,
): TaskRepositoryNetwork {

    var tasksCache: List<Task>? = null

    override suspend fun fetchAllTasks(): List<Task> {
        if(tasksCache == null) {
           tasksCache = Json.decodeFromString(taskNetworkApi.fetchAllTasks())
        }
        return tasksCache ?: listOf()
    }
}