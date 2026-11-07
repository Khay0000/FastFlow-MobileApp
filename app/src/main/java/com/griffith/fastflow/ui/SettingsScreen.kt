package com.griffith.fastflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    // State that remembers the currently selected schedule
    var selectedSchedule by remember { mutableStateOf("16:8") }

    // Available schedule options
    val schedules = listOf("12:12", "14:10", "16:8", "18:6")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shows the currently selected schedule
        Text(
            text = "Preferred schedule: $selectedSchedule",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Radio buttons for each schedule option
        schedules.forEach { schedule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedSchedule == schedule),
                        onClick = { selectedSchedule = schedule }
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedSchedule == schedule),
                    onClick = { selectedSchedule = schedule }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = schedule,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Existing Back button
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}