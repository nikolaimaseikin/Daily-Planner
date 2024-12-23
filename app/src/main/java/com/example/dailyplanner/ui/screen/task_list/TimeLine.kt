package com.example.dailyplanner.ui.screen.task_list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dailyplanner.domain.repository.Task
import java.util.Calendar

@Composable
fun TimeLine(
    tasks: List<Task>,
    selectedDate: Long,
    onTaskClick: (task: Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val taskListState: LazyListState = rememberLazyListState()

    LazyColumn(state = taskListState) {
        items(count = 24) {hour ->
            TimeLineItem(hour)
            TaskListContainer(
                tasks = tasks.filter { it.endDate in getStartTime(selectedDate, hour) until getEndTime(selectedDate, hour) },
                onTaskClick = { task -> onTaskClick(task) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

private fun getStartTime(selectedDate: Long, hour: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = selectedDate
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startTime = calendar.timeInMillis
    return  startTime
}

private fun getEndTime(selectedDate: Long, hour: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = selectedDate
    calendar.set(Calendar.HOUR_OF_DAY, hour + 1)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val endTime = calendar.timeInMillis
    return  endTime
}