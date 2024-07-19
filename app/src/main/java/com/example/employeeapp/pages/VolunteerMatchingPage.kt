package com.example.employeeapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employeeapp.common.Event
import com.example.employeeapp.common.getCurrentDate

@Composable
fun VolunteerMatchingPage(navController: NavController) {
    val events = remember { mutableStateListOf(
        Event("Volunteering", "Help at the festival", "Festival Grounds, TX", listOf("Coordination"), "High", getCurrentDate()),
        Event("Meeting", "Discuss community plans", "Community Center, TX", listOf("Planning"), "Medium", getCurrentDate()),
        Event("Workshop", "Learn new skills", "Library, TX", listOf("Teaching"), "Low", getCurrentDate())
    )}

    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Volunteer Matching", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(0.5f)) {
            items(events) { event ->
                EventListItem(event, isSelected = selectedEvent == event) {
                    selectedEvent = event
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedEvent != null) {
            Text(text = "Matched Volunteers:", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            val matchedVolunteers = when (selectedEvent?.title) {
                "Volunteering" -> listOf("Christopher Reeve", "Henry Cavill", "George Reeves")
                "Meeting" -> listOf("Adam West", "Christian Bale", "Ben Affleck")
                else -> listOf("No volunteers available based on event criteria.")
            }

            LazyColumn(modifier = Modifier.weight(0.5f)) {
                items(matchedVolunteers) { volunteer ->
                    Text(
                        text = volunteer,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { navController.navigate("volunteerProfile/$volunteer") },
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EventListItem(event: Event, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor)
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Text(text = event.title, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = event.location, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Urgency: ${event.urgency}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Date: ${event.date}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
