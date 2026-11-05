package com.griffith.fastflow.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HistoryScreen(
    onBack: () -> Unit     // Function that will be called when Back button is clicked
) {
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

        // Placeholder text for now
        Text(text = "No fasting sessions yet.")

        Spacer(modifier = Modifier.height(32.dp))

        // Back button
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}