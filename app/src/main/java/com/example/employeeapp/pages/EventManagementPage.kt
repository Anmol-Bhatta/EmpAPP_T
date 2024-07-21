package com.example.employeeapp.pages

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employeeapp.MainActivity
import com.example.employeeapp.common.Event
import com.example.employeeapp.common.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EventManagementPage(navController: NavController, context: Context, snackbarHostState: SnackbarHostState, scope: CoroutineScope) {
    val events = remember { mutableStateListOf(
        Event("Volunteering", "Help at the festival", "Festival Grounds, TX", listOf("Coordination"), "High", getCurrentDate()),
        Event("Meeting", "Discuss community plans", "Community Center, TX", listOf("Planning"), "Medium", getCurrentDate()),
        Event("Workshop", "Learn new skills", "Library, TX", listOf("Teaching"), "Low", getCurrentDate())
    )}

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Event Management", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(events) { event ->
                        EventListItem(event) {
                            navController.navigate("editEvent/${event.title}")
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate("createEvent") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Event")
                }
            }
        }
    )
}

@Composable
fun EventListItem(event: Event, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateOrEditEventPage(navController: NavController, context: Context, snackbarHostState: SnackbarHostState, scope: CoroutineScope, event: Event? = null) {
    var eventName by remember { mutableStateOf(event?.title ?: "") }
    var eventDescription by remember { mutableStateOf(event?.description ?: "") }
    var location by remember { mutableStateOf(event?.location ?: "") }
    var selectedSkills by remember { mutableStateOf(event?.skills ?: listOf<String>()) }
    var urgency by remember { mutableStateOf(event?.urgency ?: "") }
    var eventDate by remember { mutableStateOf(event?.date ?: "") }

    var eventNameError by remember { mutableStateOf(false) }
    var eventDescriptionError by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf(false) }
    var skillsError by remember { mutableStateOf(false) }
    var urgencyError by remember { mutableStateOf(false) }
    var eventDateError by remember { mutableStateOf(false) }

    val urgencies = listOf("Low", "Medium", "High")
    val availableSkills = listOf("Coordination", "Planning", "Teaching", "Communication", "Leadership")

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            eventDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    fun validateFields(): Boolean {
        eventNameError = eventName.isBlank()
        eventDescriptionError = eventDescription.isBlank()
        locationError = location.isBlank()
        skillsError = selectedSkills.isEmpty()
        urgencyError = urgency.isBlank()
        eventDateError = eventDate.isBlank()

        return !(eventNameError || eventDescriptionError || locationError || skillsError || urgencyError || eventDateError)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Event Form", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = eventName,
                    onValueChange = { eventName = it },
                    label = { Text("Event Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = eventNameError
                )
                if (eventNameError) {
                    Text(text = "Event Name is required", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = eventDescription,
                    onValueChange = { eventDescription = it },
                    label = { Text("Event Description") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = eventDescriptionError
                )
                if (eventDescriptionError) {
                    Text(text = "Event Description is required", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = locationError
                )
                if (locationError) {
                    Text(text = "Location is required", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                var skillsExpanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = selectedSkills.joinToString(),
                        onValueChange = { /* Do nothing */ },
                        label = { Text("Required Skills") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = skillsError,
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { skillsExpanded = !skillsExpanded }) {
                                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = skillsExpanded,
                        onDismissRequest = { skillsExpanded = false }
                    ) {
                        availableSkills.forEach { skill ->
                            DropdownMenuItem(
                                text = { Text(text = skill) },
                                onClick = {
                                    if (selectedSkills.contains(skill)) {
                                        selectedSkills = selectedSkills - skill
                                    } else {
                                        selectedSkills = selectedSkills + skill
                                    }
                                }
                            )
                        }
                    }
                }
                if (skillsError) {
                    Text(text = "At least one skill is required", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                var urgencyExpanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = urgency,
                        onValueChange = { /* Do nothing */ },
                        label = { Text("Urgency") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = urgencyError,
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { urgencyExpanded = !urgencyExpanded }) {
                                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = urgencyExpanded,
                        onDismissRequest = { urgencyExpanded = false }
                    ) {
                        urgencies.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(text = level) },
                                onClick = {
                                    urgency = level
                                    urgencyExpanded = false
                                }
                            )
                        }
                    }
                }
                if (urgencyError) {
                    Text(text = "Urgency is required", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = eventDate,
                    onValueChange = { eventDate = it },
                    label = { Text("Event Date") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = eventDateError,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Select Date")
                        }
                    },
                    readOnly = true
                )
                if (eventDateError) {
                    Text(text = "Event Date is required", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (event != null) {
                    Text(text = "Assigned Volunteers:")
                    Spacer(modifier = Modifier.height(8.dp))

                    val volunteers = listOf("Christopher Reeve", "Henry Cavill", "George Reeves")

                    LazyColumn(modifier = Modifier.weight(0.5f)) {
                        items(volunteers) { volunteer ->
                            Text(text = volunteer, modifier = Modifier.padding(8.dp), fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = {
                        if (validateFields()) {
                            // Save the event
                            navController.navigate("eventManagement")
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Event $eventName on $eventDate was edited. Click to view changes in the app.",
                                    actionLabel = "View",
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Event")
                }
            }
        }
    )
}

@Composable
fun EventInfoPage(navController: NavController, eventName: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope) {
    val events = listOf(
        Event("Volunteering", "Help at the festival", "Festival Grounds, TX", listOf("Coordination"), "High", getCurrentDate()),
        Event("Meeting", "Discuss community plans", "Community Center, TX", listOf("Planning"), "Medium", getCurrentDate()),
        Event("Workshop", "Learn new skills", "Library, TX", listOf("Teaching"), "Low", getCurrentDate())
    )

    val event = events.find { it.title == eventName }

    LaunchedEffect(Unit) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = "You have been added to an event on ${event?.date}. Click to view.",
                actionLabel = "View",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    if (event != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Event Information", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Event Name: ${event.title}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Event Description: ${event.description}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location: ${event.location}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Required Skills: ${event.skills.joinToString()}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Urgency: ${event.urgency}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Event Date: ${event.date}", fontSize = 16.sp)
        }
    } else {
        Text(text = "No event information available", fontSize = 16.sp, color = Color.Red)
    }
}
