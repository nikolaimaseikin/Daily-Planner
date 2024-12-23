package com.example.dailyplanner.ui.screen.upsert_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.use_cases.FetchAllTasksUseCase
import com.example.dailyplanner.domain.use_cases.UpsertTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import javax.inject.Inject

@HiltViewModel
class UpsertTaskViewModel @Inject constructor(
    val upsertTaskUseCase: UpsertTaskUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(UpsertTaskState())
    val uiState: StateFlow<UpsertTaskState> = _uiState

    fun setName(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                isModified = true
            )
        }
    }

    fun setDate(date: Long) {
        val currentDate = Calendar.getInstance()
        currentDate.timeInMillis = _uiState.value.endDate ?: 0
        val newDate = Calendar.getInstance()
        newDate.timeInMillis = date
        newDate.set(HOUR_OF_DAY, currentDate.get(HOUR_OF_DAY))
        newDate.set(MINUTE, currentDate.get(MINUTE))
        _uiState.update {
            it.copy(
                endDate = newDate.timeInMillis,
                isModified = true
            )
        }
    }

    fun setTime(hour: Int, minute: Int) {
        val endDateTime = Calendar.getInstance()
        endDateTime.timeInMillis = uiState.value.endDate
        endDateTime.set(HOUR_OF_DAY, hour)
        endDateTime.set(MINUTE, minute)
        _uiState.update {
            it.copy(
                endDate = endDateTime.timeInMillis,
                isModified = true
            )
        }
    }

    fun getCurrentDate() {
        val currentDate = Calendar.getInstance().timeInMillis
        _uiState.update {
            it.copy(
                startDate = currentDate,
                endDate = currentDate
            )
        }
    }

    fun setDescription(description: String) {
        _uiState.update {
            it.copy(
                description = description,
                isModified = true
            )
        }
    }

    fun upsertTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = getTask()
            upsertTaskUseCase.upsertTask(task)
        }
    }

    fun changeCompleteStatus() {
        _uiState.update {
            it.copy(
                isCompleted = !it.isCompleted,
                isModified = true
            )
        }
    }

    fun setTask(task: Task?) {
        task?.let { task ->
            _uiState.update {
                it.copy(
                    id = task.id,
                    name = task.name,
                    startDate = task.startDate,
                    endDate = task.endDate,
                    description = task.description,
                    isCompleted = task.isCompleted
                )
            }
        }
    }

    fun getTask(): Task {
       return Task(
            id = uiState.value.id,
            name = uiState.value.name,
            startDate = uiState.value.startDate,
            endDate = uiState.value.endDate,
            description = uiState.value.description,
            isCompleted = uiState.value.isCompleted
        )
    }

}