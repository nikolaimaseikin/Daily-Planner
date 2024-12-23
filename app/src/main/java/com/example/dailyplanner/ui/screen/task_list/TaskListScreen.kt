package com.example.dailyplanner.ui.screen.task_list

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailyplanner.domain.repository.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.dailyplanner.R
import com.example.dailyplanner.ui.screen.SharedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onDetail: (task: Task) -> Unit,
    onAddTask: () -> Unit,
    viewModel: TaskListViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val snackbarState = sharedViewModel.snackbarState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val uiState = viewModel.uiState.collectAsState()
    var showDropdownMenu by rememberSaveable { mutableStateOf(false) }

    //Выбор даты
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = uiState.value.selectedDate ?: 0
    val dateFormat = SimpleDateFormat("MMMM", Locale("ru"))

    LaunchedEffect(Unit) {
        if(!uiState.value.isLoaded) {
            viewModel.fetchAllTasksFromNetwork()
            viewModel.getCurrentDate()
            viewModel.setLoadedStatus(true)
        }
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
            TaskListTopAppBar(
                title = "${calendar.get(Calendar.DATE)} ${dateFormat.format(calendar.time)} ${calendar.get(Calendar.YEAR)}",
                showDatePicker = showDatePicker,
                dataSource = uiState.value.dataSource,
                onTitleClick = {
                    showDatePicker = !showDatePicker
                },
                onExpandDropdown = { showDropdownMenu = !showDropdownMenu }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddTask() },
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Filled.Add, "Add task")
            }
        },
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    if (showDatePicker) {
                        showDatePicker = false
                    }
                })
            },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            if(showDropdownMenu) {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    DropdownMenu (
                        expanded = showDropdownMenu,
                        onDismissRequest = { showDropdownMenu = false },
                        modifier = Modifier.width(160.dp),
                    ) {
                        DropdownMenuItem(
                            text = { Text("Локально") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.database),
                                    contentDescription = "Локально",
                                )
                            },
                            onClick = { viewModel.selectDataSource(DataSource.LOCAL) },
                        )
                        DropdownMenuItem(
                            text = { Text("Из облака") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.cloud_download),
                                    contentDescription = "Из облака"
                                )
                            },
                            onClick = { viewModel.selectDataSource(DataSource.NETWORK) }
                        )
                    }
                }
            }
            LazyColumn {
                item {
                    TaskListDatePicker(
                        visible = showDatePicker,
                        onDateSelected = {
                            viewModel.selectDate(it)
                        }
                    )
                }
            }
            TimeLine(
                tasks = if(uiState.value.dataSource == DataSource.LOCAL){
                    uiState.value.localTaskList
                } else {
                    uiState.value.networkTaskList
                },
                selectedDate = uiState.value.selectedDate ?: 0,
                onTaskClick = { task ->
                    onDetail(task)
                }
            )
        }
    }
}