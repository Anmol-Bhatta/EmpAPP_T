package com.example.employeeapp.common

import java.text.SimpleDateFormat
import java.util.*

data class Event(
    val title: String,
    val description: String,
    val location: String,
    val skills: List<String>,
    val urgency: String,
    val date: String
)

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    return sdf.format(Date())
}
