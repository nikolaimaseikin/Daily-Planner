package com.example.dailyplanner.ui.screen.task_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.domain.use_cases.FetchAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    val fetchAllTasksUseCase: FetchAllTasksUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(TaskListState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _taskList = MutableStateFlow<List<Task>>(emptyList()).flatMapLatest {
        fetchAllTasksUseCase.fetchAllTasksFromDatabase()
    }

    val uiState = combine(_uiState, _taskList){ uiState, taskList ->
        uiState.copy(
            localTaskList = taskList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskListState())

    fun fetchAllTasksFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = fetchAllTasksUseCase.fetchAllTasksFromNetwork()
            _uiState.update {
                it.copy(
                    networkTaskList = tasks
                )
            }
        }
    }

    fun getCurrentDate() {
        _uiState.update {
            it.copy(
                selectedDate = Calendar.getInstance().timeInMillis
            )
        }
    }

    fun selectDate(date: Long) {
        _uiState.update {
            it.copy(
                selectedDate = date
            )
        }
    }

    fun setLoadedStatus(status: Boolean) {
        _uiState.update {
            it.copy(
                isLoaded = status
            )
        }
    }

    fun selectDataSource(dataSource: DataSource) {
        _uiState.update {
            it.copy(
                dataSource = dataSource
            )
        }
    }
}

