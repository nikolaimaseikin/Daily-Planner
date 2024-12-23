package com.example.dailyplanner.data

import com.example.dailyplanner.data.local.TaskEntityDao
import com.example.dailyplanner.data.local.toTask
import com.example.dailyplanner.data.local.toTaskEntity
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.repository.TaskRepositoryDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryDatabaseImpl @Inject constructor(
    val taskEntityDao: TaskEntityDao,
): TaskRepositoryDatabase {

    override suspend fun upsertTask(task: Task) {
        taskEntityDao.upsertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskEntityDao.deleteTask(task.toTaskEntity())
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskEntityDao.getAllTasks().map { taskEntities ->
            taskEntities.map { taskEntity ->
                taskEntity.toTask()
            }
        }
    }

    override fun getTaskById(id: Int): Task {
        return taskEntityDao.getTaskById(id).toTask()
    }

}