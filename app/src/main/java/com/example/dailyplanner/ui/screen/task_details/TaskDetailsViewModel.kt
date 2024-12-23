package com.example.dailyplanner.ui.screen.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.use_cases.DeleteTaskUseCase
import com.example.dailyplanner.domain.use_cases.FetchAllTasksUseCase
import com.example.dailyplanner.domain.use_cases.UpsertTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    val deleteTaskUseCase: DeleteTaskUseCase,
    val upsertTaskUseCase: UpsertTaskUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(TaskDetailsState())
    val uiState: StateFlow<TaskDetailsState> = _uiState

    fun setTask(task: Task) {
        _uiState.update {
            it.copy(
                selectedTask = task
            )
        }
    }

    fun changeCompleteStatus() {
        _uiState.update {
            it.copy(
                selectedTask = it.selectedTask?.copy(
                    isCompleted = !it.selectedTask.isCompleted
                )
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.selectedTask?.let {
                upsertTaskUseCase.upsertTask(it)
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value.selectedTask?.let {
                deleteTaskUseCase.deleteTask(it)
            }
        }
    }
}