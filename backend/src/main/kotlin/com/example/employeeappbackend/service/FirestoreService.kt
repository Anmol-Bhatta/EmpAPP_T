package com.example.employeeappbackend.service

import com.example.employeeappbackend.model.*
import com.example.employeeappbackend.repository.FirestoreRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import java.util.regex.Pattern


@Service
class FirestoreService(private val firestoreRepository: FirestoreRepository) {

    private val emailPattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    )

    private val passwordPattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$"
    )

    private fun validateEmail(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        return passwordPattern.matcher(password).matches()
    }

    fun registerUser(credentials: UserCredentials, profile: UserProfile): Boolean {
        if (!validateEmail(credentials.id)) {
            throw IllegalArgumentException("Invalid email format")
        }
        if (!validatePassword(credentials.password)) {
            throw IllegalArgumentException("Password must be at least 8 characters long and include numbers, letters, and at least one special character")
        }
        return firestoreRepository.addUserCredentials(credentials) && firestoreRepository.addUserProfile(profile)
    }

    fun loginUser(credentials: UserCredentials): Boolean {
        if (!validateEmail(credentials.id)) {
            throw IllegalArgumentException("Invalid email format")
        }
        return firestoreRepository.checkUserCredentials(credentials)
    }

    fun updateUserProfile(profile: UserProfile): Boolean {
        if (!validateEmail(profile.email)) {
            throw IllegalArgumentException("Invalid email format")
        }
        return firestoreRepository.updateUserProfile(profile)
    }

    fun createEvent(event: EventDetails): Boolean {
        return firestoreRepository.addEventDetails(event)
    }

    fun matchVolunteerToEvent(volunteer: UserProfile, event: EventDetails): Boolean {
        // Matching logic based on location, skills, and availability
        if (volunteer.state == event.location && volunteer.skills.intersect(event.requiredSkills).isNotEmpty()) {
            return firestoreRepository.assignVolunteerToEvent(volunteer, event)
        }
        return false
    }

    fun notifyVolunteer(volunteer: UserProfile, message: String): Boolean {
        return firestoreRepository.sendNotification(volunteer, message)
    }

    fun trackVolunteerHistory(history: VolunteerHistory): Boolean {
        return firestoreRepository.addVolunteerHistory(history)
    }
}