package com.example.dailyplanner.data.network

interface TaskApi {
    suspend fun fetchAllTasks(): String
}