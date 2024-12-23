package com.example.dailyplanner.data.local

import com.example.dailyplanner.domain.repository.Task

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        name = this.name,
        startDate = this.startDate,
        endDate = this.endDate,
        description = this.description,
        isCompleted = this.isCompleted
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        name = this.name,
        startDate = this.startDate,
        endDate = this.endDate,
        description = this.description,
        isCompleted = this.isCompleted
    )
}