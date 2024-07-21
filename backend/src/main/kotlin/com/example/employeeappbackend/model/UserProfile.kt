package com.example.employeeappbackend.model

data class UserProfile(
    val fullName: String = "",
    val email: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipcode: String = "",
    val skills: List<String> = listOf(),
    val preferences: String = "",
    val availability: String = ""
)
