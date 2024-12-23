package com.example.dailyplanner.ui.screen.upsert_task

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailyplanner.R
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.ui.components.CustomAlertDialog
import com.example.dailyplanner.ui.screen.SharedViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertTaskScreen(
    task: Task?,
    viewModel: UpsertTaskViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    onGoBack: () -> Unit,
    onUpsert: (task: Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarState = sharedViewModel.snackbarState.collectAsState()
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var showCloseScreenAlertDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState.collectAsState()
    val dateFormat = SimpleDateFormat("dd MMMM yyyy г.", Locale("ru"))
    val timeFormat = SimpleDateFormat("HH:mm", Locale("ru"))

    LaunchedEffect(Unit) {
        task?.let {
            viewModel.setTask(task)
        } ?: viewModel.getCurrentDate()
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
                    IconButton(
                        onClick = {
                            if(uiState.value.isModified) {
                                showCloseScreenAlertDialog = true
                            } else {
                                onGoBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.upsertTask()
                            onUpsert(viewModel.getTask())
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Сохранить"
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
        if (showCloseScreenAlertDialog) {
            CustomAlertDialog(
                onDismissRequest = { showCloseScreenAlertDialog = false },
                onConfirmation = {
                    showCloseScreenAlertDialog = false
                    onGoBack()
                },
                dialogTitle = "Отменить редактирование?",
                dialogText = "Данные не будут сохранены",
                icon = Icons.Default.Warning
            )
        }
        if(showTimePicker) {
            TimePickerModal(
                onTimeSelect = { hour, minute ->
                    viewModel.setTime(hour, minute)
                    showTimePicker = false
                },
                onDismiss = {
                    showTimePicker = false
                }
            )
        }
        if(showDatePicker) {
            DatePickerModal(
                modifier = Modifier.padding(horizontal = 10.dp),
                onDateSelected = {
                    it?.let { date ->
                        viewModel.setDate(date)
                    }
                },
                onDismiss = {
                    showDatePicker = false
                }
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
                    uiState.value.name,
                    onValueChange = {
                        viewModel.setName(it)
                    }
                )
            }

            item {
                DateTime(
                    date = uiState.value.endDate.let { dateFormat.format(it) } ?: "Установить дату",
                    time = uiState.value.endDate.let { timeFormat.format(it) } ?: "Время",
                    onDateChange = { showDatePicker = true },
                    onTimeChange = { showTimePicker = true }
                )
            }

            item {
                TaskDescription(
                    value = uiState.value.description,
                    onValueChange = {
                        viewModel.setDescription(it)
                    }
                )
            }

            item {
                CompleteStatus(
                    status = uiState.value.isCompleted,
                    onStatusChange = {
                        viewModel.changeCompleteStatus()
                    }
                )
            }
        }
    }
}