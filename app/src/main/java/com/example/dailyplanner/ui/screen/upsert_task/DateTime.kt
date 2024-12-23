package com.example.dailyplanner.ui.screen.upsert_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dailyplanner.R

@Composable
fun DateTime(
    date: String,
    time: String,
    onDateChange: () -> Unit,
    onTimeChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .height(60.dp)
        ) {
            Icon(painterResource(id = R.drawable.schedule_clock), contentDescription = "Время")
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1.5f)
                .height(60.dp)
                .clickable {
                    onDateChange()
                }
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.5f)
                .height(60.dp)
                .clickable {
                    onTimeChange()
                }
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}