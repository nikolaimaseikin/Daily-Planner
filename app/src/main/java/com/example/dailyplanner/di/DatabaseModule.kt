package com.example.dailyplanner.di

import android.content.Context
import com.example.dailyplanner.data.TaskRepositoryDatabaseImpl
import com.example.dailyplanner.data.local.AppDatabase
import com.example.dailyplanner.data.local.TaskEntityDao
import com.example.dailyplanner.domain.repository.TaskRepositoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTaskEntityDao(db: AppDatabase): TaskEntityDao {
        return db.taskEntityDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskEntityDao: TaskEntityDao): TaskRepositoryDatabase {
        return TaskRepositoryDatabaseImpl(taskEntityDao)
    }
}