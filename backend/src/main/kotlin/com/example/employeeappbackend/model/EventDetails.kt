package com.example.employeeappbackend.model

data class EventDetails(
    val eventName: String = "",
    val description: String = "",
    val location: String = "",
    val requiredSkills: List<String> = listOf(),
    val urgency: String = "",
    val eventDate: String = "",
    val assignedVolunteers: List<String> = listOf()
)
