package com.example.employeeapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.employeeapp.pages.*
import com.example.employeeapp.common.Event
import com.example.employeeapp.common.getCurrentDate
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController = navController, authViewModel = authViewModel) }
        composable("signup") { SignupPage(navController = navController, authViewModel = authViewModel) }
        composable("home") { HomePage(navController = navController, authViewModel = authViewModel) }
        composable("mainScreen") { MainScreen(authViewModel = authViewModel, profileViewModel = profileViewModel, snackbarHostState = snackbarHostState, scope = scope) }
        composable("profile") { ProfilePage(navController = navController, profileViewModel = profileViewModel) }
        composable("eventManagement") { EventManagementPage(navController = navController, context = context, snackbarHostState = snackbarHostState, scope = scope) }
        composable("volunteerMatching") { VolunteerMatchingPage(navController = navController) }
        composable("history") { VolunteerHistoryPage(navController = navController) }
        composable("notifications") { NotificationsPage(navController = navController) }
        composable("createEvent") { CreateOrEditEventPage(navController = navController, context = context, snackbarHostState = snackbarHostState, scope = scope) }
        composable("editEvent/{eventName}") { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName")
            val event = findEventByName(eventName)
            CreateOrEditEventPage(navController = navController, context = context, snackbarHostState = snackbarHostState, scope = scope, event = event)
        }
        composable("volunteerProfile/{volunteerName}") { backStackEntry ->
            val volunteerName = backStackEntry.arguments?.getString("volunteerName")
            VolunteerProfilePage(navController = navController, volunteerName = volunteerName ?: "")
        }
        composable("eventInfo/{eventName}") { backStackEntry ->
            val eventName = backStackEntry.arguments?.getString("eventName")
            EventInfoPage(navController = navController, eventName = eventName ?: "", snackbarHostState = snackbarHostState, scope = scope)
        }
    }
}

// Placeholder function to simulate event lookup
fun findEventByName(eventName: String?): Event? {
    return when (eventName) {
        "Volunteering" -> Event("Volunteering", "Help at the festival", "Festival Grounds, TX", listOf("Coordination"), "High", getCurrentDate())
        "Meeting" -> Event("Meeting", "Discuss community plans", "Community Center, TX", listOf("Planning"), "Medium", getCurrentDate())
        "Workshop" -> Event("Workshop", "Learn new skills", "Library, TX", listOf("Teaching"), "Low", getCurrentDate())
        else -> null
    }
}
