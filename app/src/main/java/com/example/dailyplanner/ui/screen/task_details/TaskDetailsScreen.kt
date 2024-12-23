package com.example.dailyplanner.ui.screen.task_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailyplanner.R
import com.example.dailyplanner.domain.repository.Task
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextDecoration
import androidx.room.Delete
import com.example.dailyplanner.ui.components.CustomAlertDialog
import com.example.dailyplanner.ui.screen.SharedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    task: Task,
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onGoBack: () -> Unit,
    onEdit: (task: Task) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val snackbarState = sharedViewModel.snackbarState.collectAsState()
    var showAlertDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState.collectAsState()
    val dateFormat = SimpleDateFormat("dd MMMM yyyy г.", Locale("ru"))
    val timeFormat = SimpleDateFormat("HH:mm", Locale("ru"))

    LaunchedEffect(task) {
        viewModel.setTask(task)
    }

    LaunchedEffect(snackbarState) {
        scope.launch {
            snackbarState.value?.let {
                snackbarHostState.showSnackbar(it)
                sharedViewModel.onShown()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { onGoBack() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onEdit(task) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Редактировать задачу"
                        )
                    }
                    IconButton(
                        onClick = { showAlertDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить задачу"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (showAlertDialog) {
            CustomAlertDialog(
                onDismissRequest = { showAlertDialog = false },
                onConfirmation = {
                    showAlertDialog = false
                    viewModel.deleteTask()
                    onDelete()
                },
                dialogTitle = "Удалить задачу?",
                dialogText = "Данные будут потеряны",
                icon = Icons.Default.Delete
            )
        }
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Spacer(modifier = Modifier)
            }

            item {
                TaskName(
                    name = uiState.value.selectedTask?.name ?: "",
                    decoration = uiState.value.selectedTask?.let {
                        if (it.isCompleted) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    } ?: TextDecoration.None
                )
            }

            item {
                DateTime(
                    date = dateFormat.format(uiState.value.selectedTask?.endDate ?: 0),
                    time = timeFormat.format(uiState.value.selectedTask?.endDate ?: 0)
                )
            }

            item {
                TaskDescription(value = "${uiState.value.selectedTask?.description}")
            }

            item {
                CompleteStatus(
                    status = uiState.value.selectedTask?.isCompleted == true,
                    onStatusChange = { viewModel.changeCompleteStatus() }
                )
            }
        }
    }
}