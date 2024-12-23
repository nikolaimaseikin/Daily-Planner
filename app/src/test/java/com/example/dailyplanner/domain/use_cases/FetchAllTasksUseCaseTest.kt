package com.example.dailyplanner.domain.use_cases

import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.repository.TaskRepositoryDatabase
import com.example.dailyplanner.domain.repository.TaskRepositoryNetwork
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test


@ExperimentalCoroutinesApi
class FetchAllTasksUseCaseTest {

    private val taskRepositoryNetwork: TaskRepositoryNetwork = mockk()
    private val taskRepositoryDatabase: TaskRepositoryDatabase = mockk()
    private val fetchAllTasksUseCase = FetchAllTasksUseCase(taskRepositoryNetwork, taskRepositoryDatabase)

    @Test
    fun `should fetch all tasks from network repository`() = runBlocking {
        // Given
        val mockTasks = listOf(
            Task(0, "Задача 1", 1733139498481, 1733139517569, "Описание моего дела 1", false),
            Task(1, "Задача 2", 1733139498481, 1733139517569, "Описание моего дела 2", false)
        )
        coEvery { taskRepositoryNetwork.fetchAllTasks() } returns mockTasks

        // When
        val result = fetchAllTasksUseCase.fetchAllTasksFromNetwork()

        // Then
        coVerify { taskRepositoryNetwork.fetchAllTasks() }
        assertEquals(mockTasks, result)
    }

    @Test
    fun `should fetch all tasks from database repository`() = runBlocking {
        // Given
        val mockTasks = listOf(
            Task(0, "Задача 1", 1733139498481, 1733139517569, "Описание моего дела 1", false),
            Task(1, "Задача 2", 1733139498481, 1733139517569, "Описание моего дела 2", false)
        )
        val flowOfTasks: Flow<List<Task>> = flowOf(mockTasks)
        coEvery { taskRepositoryDatabase.getAllTasks() } returns flowOfTasks

        // When
        val result = fetchAllTasksUseCase.fetchAllTasksFromDatabase()

        // Then
        coVerify { taskRepositoryDatabase.getAllTasks() }
        result.collect { tasks ->
            assertEquals(mockTasks, tasks)
        }
    }
}
