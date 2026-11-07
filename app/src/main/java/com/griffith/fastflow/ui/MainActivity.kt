package com.griffith.fastflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.griffith.fastflow.ui.DashboardScreen
import com.griffith.fastflow.ui.HistoryScreen
import com.griffith.fastflow.ui.SettingsScreen
import com.griffith.fastflow.ui.theme.FastFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastFlowTheme {
                FastFlowApp()
            }
        }
    }
}

@Composable
fun FastFlowApp() {
    val navController = rememberNavController()

    // Shared state for completed fasting sessions
    var lastCompletedDuration by remember { mutableStateOf(0L) }
    var completedSessionCount by remember { mutableStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable("dashboard") {
            DashboardScreen(
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToSettings = { navController.navigate("settings") },
                onSessionCompleted = { duration ->
                    lastCompletedDuration = duration
                    completedSessionCount += 1
                }
            )
        }
        composable("history") {
            HistoryScreen(
                onBack = { navController.popBackStack() },
                lastCompletedDuration = lastCompletedDuration,
                completedSessionCount = completedSessionCount
            )
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}