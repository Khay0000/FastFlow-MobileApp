package com.griffith.fastflow.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.griffith.fastflow.ui.theme.FastFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This is where we start drawing the UI
        setContent {
            FastFlowTheme {          // Applies the app theme
                FastFlowApp()        // Our main navigation setup
            }
        }
    }
}

@Composable
fun FastFlowApp() {
    // This remembers the navigation state (which screen we are on)
    val navController = rememberNavController()

    // NavHost is the container that switches between screens
    NavHost(
        navController = navController,
        startDestination = "dashboard"     // First screen that appears
    ) {
        // Dashboard screen
        composable("dashboard") {
            DashboardScreen(
                onNavigateToHistory = {
                    navController.navigate("history")   // Go to History screen
                },
                onNavigateToSettings = {
                    navController.navigate("settings")  // Go to Settings screen
                }
            )
        }

        // History screen
        composable("history") {
            HistoryScreen(
                onBack = {
                    navController.popBackStack()   // Go back to previous screen
                }
            )
        }

        // Settings screen
        composable("settings") {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
