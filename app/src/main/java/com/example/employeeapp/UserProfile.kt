package com.example.employeeapp

data class UserProfile(
    val fullName: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipcode: String = "",
    val skills: List<String> = listOf(),
    val preferences: String = "",
    val availability: String = ""
)
