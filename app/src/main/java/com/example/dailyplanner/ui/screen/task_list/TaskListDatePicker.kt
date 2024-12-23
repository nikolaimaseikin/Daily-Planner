package com.example.dailyplanner.ui.screen.task_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListDatePicker(
    visible: Boolean,
    onDateSelected: (currentDateInMillis: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 300))
                + fadeIn(animationSpec = tween(durationMillis = 300))
                + expandVertically(expandFrom = Alignment.Top),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300))
                + fadeOut(animationSpec = tween(durationMillis = 300))
                + shrinkVertically(shrinkTowards = Alignment.Top),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { })
                },
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
        LaunchedEffect(datePickerState.selectedDateMillis, ) {
            datePickerState.selectedDateMillis?.let { selectedDate ->
                onDateSelected(selectedDate)
            }
        }
    }
}