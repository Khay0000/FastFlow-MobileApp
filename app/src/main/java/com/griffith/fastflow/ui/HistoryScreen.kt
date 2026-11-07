package com.griffith.fastflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    lastCompletedDuration: Long,
    completedSessionCount: Int
) {
    // Format the duration the same way as the Dashboard
    val hours = lastCompletedDuration / 3600
    val minutes = (lastCompletedDuration % 3600) / 60
    val seconds = lastCompletedDuration % 60
    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (completedSessionCount == 0) {
            Text("No fasting sessions yet.")
        } else {
            // Card showing the last completed session
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Last completed fast",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Completed",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total completed sessions: $completedSessionCount",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}