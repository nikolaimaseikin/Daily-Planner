package com.example.dailyplanner.domain.repository

interface TaskRepositoryNetwork {
    suspend fun fetchAllTasks(): List<Task>
}