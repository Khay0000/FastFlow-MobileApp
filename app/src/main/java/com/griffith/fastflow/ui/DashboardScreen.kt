package com.griffith.fastflow.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griffith.fastflow.SessionDetailActivity
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onSessionCompleted: (Long) -> Unit   // New callback that receives the completed duration
) {
    // Tracks whether a fast is currently active
    var isFasting by remember { mutableStateOf(false) }

    // Stores the time (in milliseconds) when the fast started
    var startTime by remember { mutableStateOf(0L) }

    // Stores how many seconds have passed since the fast started
    var elapsedSeconds by remember { mutableStateOf(0L) }

    // Needed to launch intents
    val context = LocalContext.current

    // Updates the timer every second while fasting is active
    LaunchedEffect(isFasting) {
        while (isFasting) {
            elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000
            delay(1000L)
        }
    }

    // Convert total seconds into hours, minutes and seconds
    val hours = elapsedSeconds / 3600
    val minutes = (elapsedSeconds % 3600) / 60
    val seconds = elapsedSeconds % 60

    // Format as HH:MM:SS with leading zeros
    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FastFlow",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Formatted timer display
        Text(
            text = formattedTime,
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isFasting) "Fasting in progress..." else "Ready to fast"
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Start / Stop button
        Button(
            onClick = {
                if (!isFasting) {
                    // Start the fast
                    startTime = System.currentTimeMillis()
                    elapsedSeconds = 0L
                    isFasting = true
                } else {
                    // Stop the fast
                    elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000
                    onSessionCompleted(elapsedSeconds)   // Report the completed duration
                    isFasting = false
                }
            }
        ) {
            Text(if (isFasting) "Stop Fast" else "Start Fast")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Share Progress button (Implicit Intent)
        OutlinedButton(
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "I've been fasting for $formattedTime using FastFlow!"
                    )
                }
                context.startActivity(
                    Intent.createChooser(shareIntent, "Share your progress")
                )
            }
        ) {
            Text("Share Progress")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // View Session Details button (Explicit Intent)
        OutlinedButton(
            onClick = {
                val intent = Intent(context, SessionDetailActivity::class.java).apply {
                    putExtra("formattedTime", formattedTime)
                }
                context.startActivity(intent)
            }
        ) {
            Text("View Session Details")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(onClick = onNavigateToHistory) {
                Text("History")
            }
            OutlinedButton(onClick = onNavigateToSettings) {
                Text("Settings")
            }
        }
    }
}