package com.example.employeeappbackend.controller

import com.example.employeeappbackend.model.UserProfile
import com.example.employeeappbackend.service.FirestoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class FirestoreController(private val firestoreService: FirestoreService) {

    @PostMapping("/add")
    fun addUserProfile(@RequestBody profile: UserProfile): ResponseEntity<String> {
        return if (firestoreService.addUserProfile(profile)) {
            ResponseEntity.ok("Profile added successfully")
        } else {
            ResponseEntity.badRequest().body("Failed to add profile")
        }
    }

    @GetMapping("/{fullName}")
    fun getUserProfile(@PathVariable fullName: String): ResponseEntity<UserProfile> {
        val profile = firestoreService.getUserProfile(fullName)
        return if (profile != null) {
            ResponseEntity.ok(profile)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update")
    fun updateUserProfile(@RequestBody profile: UserProfile): ResponseEntity<String> {
        return if (firestoreService.updateUserProfile(profile)) {
            ResponseEntity.ok("Profile updated successfully")
        } else {
            ResponseEntity.badRequest().body("Failed to update profile")
        }
    }
}
