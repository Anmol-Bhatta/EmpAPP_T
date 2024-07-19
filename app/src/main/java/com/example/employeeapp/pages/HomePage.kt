package com.example.employeeapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employeeapp.AuthState
import com.example.employeeapp.AuthViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState by authViewModel.authState.observeAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                DrawerHeader()
                Spacer(modifier = Modifier.height(16.dp))
                DrawerBody(navController = navController)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Home Page") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Upcoming Events for ${getCurrentDate()}", fontSize = 24.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        EventsList()

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = {
                            authViewModel.signout()
                        }) {
                            Text(text = "Sign out")
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Navigation", fontSize = 24.sp)
    }
}

@Composable
fun DrawerBody(navController: NavController) {
    Column {
        TextButton(onClick = { navController.navigate("profile") }) {
            Text(text = "Profile Management", fontSize = 18.sp)
        }
        TextButton(onClick = { navController.navigate("eventManagement") }) {
            Text(text = "Event Management", fontSize = 18.sp)
        }
        TextButton(onClick = { navController.navigate("volunteerMatching") }) {
            Text(text = "Volunteer Matching", fontSize = 18.sp)
        }
        TextButton(onClick = { navController.navigate("history") }) {
            Text(text = "Volunteer History", fontSize = 18.sp)
        }
    }
}

@Composable
fun EventsList() {
    val events = listOf(
        Event("Volunteering", "11:00 AM - 12:00 PM", "Festival Grounds, TX"),
        Event("Meeting", "1:00 PM - 2:00 PM", "Community Center, TX"),
        Event("Workshop", "3:00 PM - 5:00 PM", "Library, TX")
    )

    LazyColumn {
        items(events) { event ->
            EventItem(event)
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(text = event.title, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = event.duration, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = event.location, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

data class Event(val title: String, val duration: String, val location: String)

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    return sdf.format(Date())
}
