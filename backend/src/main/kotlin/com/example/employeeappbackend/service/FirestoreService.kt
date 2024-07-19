package com.example.employeeappbackend.service

import com.example.employeeappbackend.model.*
import com.example.employeeappbackend.repository.FirestoreRepository
import org.springframework.stereotype.Service

@Service
class FirestoreService(private val firestoreRepository: FirestoreRepository) {

    fun addUserCredentials(credentials: UserCredentials): Boolean {
        return firestoreRepository.addUserCredentials(credentials)
    }

    fun addUserProfile(profile: UserProfile): Boolean {
        return firestoreRepository.addUserProfile(profile)
    }

    fun addEventDetails(event: EventDetails): Boolean {
        return firestoreRepository.addEventDetails(event)
    }

    fun addVolunteerHistory(history: VolunteerHistory): Boolean {
        return firestoreRepository.addVolunteerHistory(history)
    }

    fun addState(state: State): Boolean {
        return firestoreRepository.addState(state)
    }
}
