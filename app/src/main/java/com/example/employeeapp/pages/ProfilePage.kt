package com.example.employeeapp.pages

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employeeapp.ProfileViewModel
import java.util.*
import androidx.compose.foundation.rememberScrollState

@Composable
fun ProfilePage(navController: NavController, profileViewModel: ProfileViewModel) {
    var fullName by remember { mutableStateOf("") }
    var address1 by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var selectedSkills by remember { mutableStateOf(listOf<String>()) }
    var preferences by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf(listOf<String>()) }

    var fullNameError by remember { mutableStateOf(false) }
    var address1Error by remember { mutableStateOf(false) }
    var cityError by remember { mutableStateOf(false) }
    var stateError by remember { mutableStateOf(false) }
    var zipCodeError by remember { mutableStateOf(false) }
    var skillsError by remember { mutableStateOf(false) }
    var availabilityError by remember { mutableStateOf(false) }

    val states = listOf("AL", "AK", "AZ", "AR", "CA") // Add all state codes here
    val availableSkills = listOf("Programming", "Guitar", "Teaching", "Mathematics", "Carpentry")

    fun validateFields(): Boolean {
        fullNameError = fullName.isBlank()
        address1Error = address1.isBlank()
        cityError = city.isBlank()
        stateError = state.isBlank()
        zipCodeError = zipCode.length < 5
        skillsError = selectedSkills.isEmpty()
        availabilityError = availability.isEmpty()

        return !(fullNameError || address1Error || cityError || stateError || zipCodeError || skillsError || availabilityError)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Page", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = fullNameError,
            singleLine = true
        )
        if (fullNameError) {
            Text(text = "Full Name is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address1,
            onValueChange = { address1 = it },
            label = { Text("Address 1") },
            modifier = Modifier.fillMaxWidth(),
            isError = address1Error,
            singleLine = true
        )
        if (address1Error) {
            Text(text = "Address 1 is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address2,
            onValueChange = { address2 = it },
            label = { Text("Address 2") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth(),
            isError = cityError,
            singleLine = true
        )
        if (cityError) {
            Text(text = "City is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = state,
                onValueChange = { /* Do nothing */ },
                label = { Text("State") },
                modifier = Modifier.fillMaxWidth(),
                isError = stateError,
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                states.forEach { stateCode ->
                    DropdownMenuItem(
                        text = { Text(text = stateCode) },
                        onClick = {
                            state = stateCode
                            expanded = false
                        }
                    )
                }
            }
        }
        if (stateError) {
            Text(text = "State is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = zipCode,
            onValueChange = { zipCode = it },
            label = { Text("Zip Code") },
            modifier = Modifier.fillMaxWidth(),
            isError = zipCodeError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (zipCodeError) {
            Text(text = "Zip Code must be at least 5 characters", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        var skillsExpanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = selectedSkills.joinToString(),
                onValueChange = { /* Do nothing */ },
                label = { Text("Skills") },
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

        OutlinedTextField(
            value = preferences,
            onValueChange = { preferences = it },
            label = { Text("Preferences") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var selectedDate by remember { mutableStateOf("") }

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                availability = availability + selectedDate
            }, year, month, day
        )

        OutlinedTextField(
            value = selectedDate,
            onValueChange = { selectedDate = it },
            label = { Text("Add Availability Date") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialog.show()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Select Date")
                }
            },
            readOnly = true
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            availability.forEach { date ->
                Text(text = date, modifier = Modifier.padding(4.dp))
            }
        }

        if (availabilityError) {
            Text(text = "At least one availability date is required", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    profileViewModel.updateProfile(
                        fullName,
                        address1,
                        address2,
                        city,
                        state,
                        zipCode,
                        selectedSkills,
                        preferences,
                        availability
                    )
                    navController.navigate("home")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Profile")
        }
    }
}
