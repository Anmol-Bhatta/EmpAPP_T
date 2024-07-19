package com.example.employeeapp.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.employeeapp.AuthViewModel
import com.example.employeeapp.ProfileViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("home") { HomePage(navController = navController, authViewModel = authViewModel) }
            composable("profile") { ProfilePage(navController = navController, profileViewModel = profileViewModel) }
            composable("eventManagement") { EventManagementPage(navController = navController, context = context, snackbarHostState = snackbarHostState, scope = scope) }
            composable("volunteerMatching") { VolunteerMatchingPage(navController = navController) }
            composable("notifications") { NotificationsPage(navController = navController) }
            composable("history") { VolunteerHistoryPage(navController = navController) }
        }
    }
}
