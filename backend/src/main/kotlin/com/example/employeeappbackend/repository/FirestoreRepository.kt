package com.example.employeeappbackend.repository

import com.example.employeeappbackend.model.*
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Repository
import org.springframework.security.crypto.bcrypt.BCrypt
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

    fun addState(state: State): Boolean {
        return try {
            db.collection("States")
                .document(state.code)
                .set(state)
                .get()
            true
        } catch (e: ExecutionException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }
}
