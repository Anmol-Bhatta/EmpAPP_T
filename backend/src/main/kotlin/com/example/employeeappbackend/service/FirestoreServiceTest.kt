package com.example.employeeappbackend.service

import com.example.employeeappbackend.model.*
import com.example.employeeappbackend.repository.FirestoreRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class FirestoreServiceTest {

    @Mock
    private lateinit var firestoreRepository: FirestoreRepository

    @InjectMocks
    private lateinit var firestoreService: FirestoreService

    private lateinit var userCredentials: UserCredentials
    private lateinit var userProfile: UserProfile
    private lateinit var eventDetails: EventDetails
    private lateinit var volunteerHistory: VolunteerHistory

    @BeforeEach
    fun setUp() {
        userCredentials = UserCredentials(id = "test@example.com", password = "Password1!")
        userProfile = UserProfile(
            fullName = "Test User",
            email = "test@example.com",
            address = "123 Test St",
            city = "Test City",
            state = "TX",
            zipcode = "12345",
            skills = listOf("Skill1", "Skill2"),
            preferences = "Preferences",
            availability = "Weekends"
        )
        eventDetails = EventDetails(
            eventName = "Test Event",
            description = "Test Description",
            location = "TX",
            requiredSkills = listOf("Skill1"),
            urgency = "High",
            eventDate = "2024-07-20",
            assignedVolunteers = listOf()
        )
        volunteerHistory = VolunteerHistory(
            volunteerName = "Test User",
            eventName = "Test Event",
            dateParticipated = "2024-07-20"
        )
    }

    @Test
    fun registerUser_validData() {
        `when`(firestoreRepository.addUserCredentials(userCredentials)).thenReturn(true)
        `when`(firestoreRepository.addUserProfile(userProfile)).thenReturn(true)

        val result = firestoreService.registerUser(userCredentials, userProfile)
        assertTrue(result)
    }

    @Test
    fun registerUser_invalidEmail() {
        val invalidCredentials = userCredentials.copy(id = "invalid-email")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            firestoreService.registerUser(invalidCredentials, userProfile)
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun registerUser_invalidPassword() {
        val invalidCredentials = userCredentials.copy(password = "weakpass")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            firestoreService.registerUser(invalidCredentials, userProfile)
        }
        assertEquals("Password must be at least 8 characters long and include numbers, letters, and at least one special character", exception.message)
    }

    @Test
    fun loginUser_validData() {
        `when`(firestoreRepository.checkUserCredentials(userCredentials)).thenReturn(true)

        val result = firestoreService.loginUser(userCredentials)
        assertTrue(result)
    }

    @Test
    fun loginUser_invalidEmail() {
        val invalidCredentials = userCredentials.copy(id = "invalid-email")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            firestoreService.loginUser(invalidCredentials)
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun updateUserProfile_validData() {
        `when`(firestoreRepository.updateUserProfile(userProfile)).thenReturn(true)

        val result = firestoreService.updateUserProfile(userProfile)
        assertTrue(result)
    }

    @Test
    fun updateUserProfile_invalidEmail() {
        val invalidProfile = userProfile.copy(email = "invalid-email")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            firestoreService.updateUserProfile(invalidProfile)
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun createEvent_validData() {
        `when`(firestoreRepository.addEventDetails(eventDetails)).thenReturn(true)

        val result = firestoreService.createEvent(eventDetails)
        assertTrue(result)
    }

    @Test
    fun matchVolunteers_validData() {
        val volunteers = listOf(
            userProfile.copy(fullName = "Volunteer1", skills = listOf("Skill1"), availability = "2024-07-20"),
            userProfile.copy(fullName = "Volunteer2", skills = listOf("Skill2"), availability = "2024-07-21")
        )
        `when`(firestoreRepository.getAllVolunteers()).thenReturn(volunteers)

        val result = firestoreService.matchVolunteersToEvent(eventDetails)
        assertEquals(1, result.size)
        assertEquals("Volunteer1", result[0].fullName)
    }

    @Test
    fun sendNotification_validData() {
        `when`(firestoreRepository.sendNotification(userProfile, "Event Reminder")).thenReturn(true)

        val result = firestoreService.sendNotification(userProfile, "Event Reminder")
        assertTrue(result)
    }

    @Test
    fun addVolunteerHistory_validData() {
        `when`(firestoreRepository.addVolunteerHistory(volunteerHistory)).thenReturn(true)

        val result = firestoreService.addVolunteerHistory(volunteerHistory)
        assertTrue(result)
    }

    @Test
    fun getVolunteerHistory_validData() {
        val history = listOf(volunteerHistory)
        `when`(firestoreRepository.getVolunteerHistory(userProfile.fullName)).thenReturn(history)

        val result = firestoreService.getVolunteerHistory(userProfile.fullName)
        assertEquals(history, result)
    }
}
