package com.example.dailyplanner.ui.screen.task_list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import com.example.dailyplanner.domain.repository.Task

@Composable
fun TaskListContainer(
    tasks: List<Task>?,
    onTaskClick: (task: Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        //.height(200.dp)
        .fillMaxWidth()
        .padding(start = 60.dp)
        .clip(shape = ShapeDefaults.Medium)
        .background(color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f))
    ){
        Spacer(modifier.padding(3.dp))
        tasks?.forEach {
            TaskItem(it, onClick = { task -> onTaskClick(task) }, onComplete = {})
        }
        Spacer(modifier.padding(3.dp))
    }
}