package com.example.dailyplanner.di

import com.example.dailyplanner.data.TaskRepositoryNetworkImpl
import com.example.dailyplanner.data.network.TaskApi
import com.example.dailyplanner.data.network.TaskNetworkMock
import com.example.dailyplanner.domain.repository.TaskRepositoryNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideTranslationApi(): TaskApi {
        return TaskNetworkMock()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskApi: TaskApi): TaskRepositoryNetwork {
        return TaskRepositoryNetworkImpl(taskApi)
    }
}