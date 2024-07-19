package com.example.employeeapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment


@Composable
fun VolunteerProfilePage(navController: NavController, volunteerName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Volunteer Profile", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Name: $volunteerName", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Dummy data for profile details
        Text(text = "Full Name: Christopher Reeve", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Address 1: 123 Main St", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Address 2: Apt 4B", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "City: Springfield", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "State: IL", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Zip Code: 62701", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Skills: Coordination, Planning", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Preferences: None", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Availability: Mondays, Wednesdays", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}
