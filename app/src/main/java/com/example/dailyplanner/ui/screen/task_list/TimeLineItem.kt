package com.example.dailyplanner.ui.screen.task_list

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun TimeLineItem(
    hour: Int,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    var size by remember { mutableStateOf(IntSize(1, 1)) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(20.dp)
        //.border(width = 2.dp, color = Color.Red),
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                   size = it.size
                }
                //.border(width = 1.dp, color = Color.Green),
        ) {
            drawText(
                textMeasurer = textMeasurer,
                text = "${if (hour < 10) "0$hour" else "$hour"}:00",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                ),
                topLeft = Offset(
                    x = size.width.toFloat() * 0.02f,
                    y = (size.height.toFloat() / 2) - 30f
                )
            )
            drawLine(
                start = Offset(x = size.width.toFloat() * 0.02f + 150f, y = size.height.toFloat() / 2),
                end = Offset(x = size.width.toFloat(), y = size.height.toFloat() / 2),
                color = Color.Gray,
                alpha = 0.5f,
                strokeWidth = 2f
            )
        }
    }
}