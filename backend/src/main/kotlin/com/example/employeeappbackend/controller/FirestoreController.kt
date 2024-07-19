package com.example.employeeappbackend.controller

import com.example.employeeappbackend.model.*
import com.example.employeeappbackend.service.FirestoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/firestore")
class FirestoreController(private val firestoreService: FirestoreService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody credentials: UserCredentials): ResponseEntity<String> {
        return if (firestoreService.addUserCredentials(credentials)) {
            ResponseEntity.ok("User registered successfully")
        } else {
            ResponseEntity.status(500).body("Error registering user")
        }
    }

    @PostMapping("/addUserCredentials")
    fun addUserCredentials(@RequestBody credentials: UserCredentials): ResponseEntity<String> {
        return if (firestoreService.addUserCredentials(credentials)) {
            ResponseEntity.ok("User credentials added successfully")
        } else {
            ResponseEntity.status(500).body("Error adding user credentials")
        }
    }

    @PostMapping("/addUserProfile")
    fun addUserProfile(@RequestBody profile: UserProfile): ResponseEntity<String> {
        return if (firestoreService.addUserProfile(profile)) {
            ResponseEntity.ok("User profile added successfully")
        } else {
            ResponseEntity.status(500).body("Error adding user profile")
        }
    }

    @PostMapping("/addEventDetails")
    fun addEventDetails(@RequestBody event: EventDetails): ResponseEntity<String> {
        return if (firestoreService.addEventDetails(event)) {
            ResponseEntity.ok("Event details added successfully")
        } else {
            ResponseEntity.status(500).body("Error adding event details")
        }
    }

    @PostMapping("/addVolunteerHistory")
    fun addVolunteerHistory(@RequestBody history: VolunteerHistory): ResponseEntity<String> {
        return if (firestoreService.addVolunteerHistory(history)) {
            ResponseEntity.ok("Volunteer history added successfully")
        } else {
            ResponseEntity.status(500).body("Error adding volunteer history")
        }
    }

    @PostMapping("/addState")
    fun addState(@RequestBody state: State): ResponseEntity<String> {
        return if (firestoreService.addState(state)) {
            ResponseEntity.ok("State added successfully")
        } else {
            ResponseEntity.status(500).body("Error adding state")
        }
    }
}
