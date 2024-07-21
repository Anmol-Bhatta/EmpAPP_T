package com.example.employeeappbackend.repository

import com.example.employeeappbackend.model.*
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Repository
import java.util.concurrent.ExecutionException

@Repository
class FirestoreRepository {

    private val db = FirestoreClient.getFirestore()

    fun addUserCredentials(credentials: UserCredentials): Boolean {
        val hashedPassword = BCrypt.hashpw(credentials.password, BCrypt.gensalt())
        val securedCredentials = credentials.copy(password = hashedPassword)
        return try {
            db.collection("UserCredentials")
                .document(credentials.id)
                .set(securedCredentials)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }

    fun checkUserCredentials(credentials: UserCredentials): Boolean {
        return try {
            val document = db.collection("UserCredentials")
                .document(credentials.id)
                .get()
                .get()
            if (document.exists()) {
                val storedCredentials = document.toObject(UserCredentials::class.java)
                storedCredentials != null && BCrypt.checkpw(credentials.password, storedCredentials.password)
            } else {
                false
            }
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }

    fun addUserProfile(profile: UserProfile): Boolean {
        return try {
            db.collection("UserProfile")
                .document(profile.fullName)
                .set(profile)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }

    fun updateUserProfile(profile: UserProfile): Boolean {
        return try {
            db.collection("UserProfile")
                .document(profile.fullName)
                .set(profile)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }

    fun addEventDetails(event: EventDetails): Boolean {
        return try {
            db.collection("EventDetails")
                .document(event.eventName)
                .set(event)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }

    fun assignVolunteerToEvent(volunteer: UserProfile, event: EventDetails): Boolean {
        return try {
            val updatedEvent = event.copy(assignedVolunteers = event.assignedVolunteers + volunteer.fullName)
            db.collection("EventDetails")
                .document(event.eventName)
                .set(updatedEvent)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }

    fun sendNotification(volunteer: UserProfile, message: String): Boolean {
        // This is a placeholder for actual notification sending logic
        println("Sending notification to ${volunteer.email}: $message")
        return true
    }

    fun addVolunteerHistory(history: VolunteerHistory): Boolean {
        return try {
            db.collection("VolunteerHistory")
                .add(history)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }
}

