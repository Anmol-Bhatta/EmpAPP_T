package com.example.employeeapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employeeapp.common.Event
import com.example.employeeapp.common.getCurrentDate

@Composable
fun VolunteerHistoryPage(navController: NavController) {
    val events = listOf(
        Event("Volunteering", "Help at the festival", "Festival Grounds, TX", listOf("Coordination"), "High", getCurrentDate()),
        Event("Meeting", "Discuss community plans", "Community Center, TX", listOf("Planning"), "Medium", getCurrentDate()),
        Event("Workshop", "Learn new skills", "Library, TX", listOf("Teaching"), "Low", getCurrentDate())
    )

    val volunteersForEvents = mapOf(
        "Volunteering" to listOf("Christopher Reeve (Volunteered)", "Henry Cavill (Absent)", "George Reeves (Volunteered)"),
        "Meeting" to listOf("Adam West (Volunteered)", "Christian Bale (Volunteered)", "Ben Affleck (Absent)"),
        "Workshop" to listOf("No volunteers available based on event criteria")
    )

    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Volunteer History", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Events for ${getCurrentDate()}:")
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(0.5f)) {
            items(events) { event ->
                EventListItem(event, isSelected = selectedEvent == event) {
                    selectedEvent = event
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Assigned Volunteers:")
        Spacer(modifier = Modifier.height(8.dp))

        val volunteers = selectedEvent?.let { volunteersForEvents[it.title] } ?: emptyList()
        if (volunteers.isEmpty()) {
            Text(text = "No volunteers available based on event criteria", color = Color.Red)
        } else {
            LazyColumn(modifier = Modifier.weight(0.5f)) {
                items(volunteers) { volunteer ->
                    VolunteerHistoryListItem(volunteer)
                }
            }
        }
    }
}

@Composable
fun VolunteerHistoryListItem(volunteer: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(text = volunteer, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
