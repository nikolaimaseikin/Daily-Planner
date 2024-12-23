package com.example.dailyplanner.ui.screen.task_list

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.dailyplanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListTopAppBar(
    title: String,
    showDatePicker: Boolean,
    dataSource: DataSource,
    onTitleClick: () -> Unit,
    onExpandDropdown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val rotation = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(showDatePicker) {
        rotation.animateTo(
            targetValue = if (showDatePicker) -180f else 0f,
            animationSpec = tween(durationMillis = 300),
        )
    }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(220.dp)
                    .clickable(onClick = {
                        scope.launch {
                            rotation.animateTo(
                                targetValue = if (showDatePicker) 0f else -180f,
                                animationSpec = tween(durationMillis = 300),
                            )
                        }
                        onTitleClick()
                    }
                ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, modifier = Modifier.weight(2f))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Open calendar",
                    modifier = Modifier
                        .weight(0.5f)
                        .graphicsLayer {
                            rotationZ = rotation.value
                        }
                )
            }
        },
        actions = {
            IconButton(onClick = { onExpandDropdown() }) {
                Icon(
                    painter = painterResource(
                        if (dataSource == DataSource.NETWORK) {
                            R.drawable.cloud_download
                        } else {
                            R.drawable.database
                        }
                    ),
                    contentDescription = "Выбрать источник загрузки задач"
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}
