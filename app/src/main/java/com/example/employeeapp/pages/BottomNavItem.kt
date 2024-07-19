// BottomNavItem.kt
package com.example.employeeapp.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
    object EventManagement : BottomNavItem("eventManagement", Icons.Default.AccountCircle, "Event Management")
    object VolunteerMatching : BottomNavItem("volunteerMatching", Icons.Default.Person, "Volunteer Matching")
    object Notifications : BottomNavItem("notifications", Icons.Default.Notifications, "Notifications")
    object History : BottomNavItem("history", Icons.Default.Info, "History")
}
