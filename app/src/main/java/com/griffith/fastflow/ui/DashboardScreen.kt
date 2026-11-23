package com.griffith.fastflow.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

    // 16-hour fasting goal
    val fastingGoalSeconds = 16 * 60 * 60L
    val progress = (elapsedSeconds.toFloat() / fastingGoalSeconds).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "FastFlow",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Your fasting journey",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Timer inside a circular progress indicator
        Card(
            modifier = Modifier.size(260.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(220.dp),
                    strokeWidth = 12.dp,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = formattedTime,
                        fontSize = 40.sp,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "16h goal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isFasting) "Fasting in progress..." else "Ready to fast",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(if (isFasting) "Stop Fast" else "Start Fast")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Share Progress and View Details side by side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Share Progress")
            }

            OutlinedButton(
                onClick = {
                    val intent = Intent(context, SessionDetailActivity::class.java).apply {
                        putExtra("formattedTime", formattedTime)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("View Details")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // History and Settings side by side with equal widths
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onNavigateToHistory,
                modifier = Modifier.weight(1f)
            ) {
                Text("History")
            }
            OutlinedButton(
                onClick = onNavigateToSettings,
                modifier = Modifier.weight(1f)
            ) {
                Text("Settings")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}