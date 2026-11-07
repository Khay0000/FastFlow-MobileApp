package com.griffith.fastflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griffith.fastflow.ui.theme.FastFlowTheme

class SessionDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the duration that was passed from the Dashboard
        val formattedTime = intent.getStringExtra("formattedTime") ?: "00:00:00"

        setContent {
            FastFlowTheme {
                SessionDetailScreen(formattedTime = formattedTime)
            }
        }
    }
}

@Composable
fun SessionDetailScreen(formattedTime: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Session Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Current fasting duration",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = formattedTime,
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "FastFlow fasting session",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}