package com.example.employeeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ProfileViewModel : ViewModel() {
    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> get() = _fullName

    private val _address1 = MutableLiveData<String>()
    val address1: LiveData<String> get() = _address1

    private val _address2 = MutableLiveData<String>()
    val address2: LiveData<String> get() = _address2

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> get() = _state

    private val _zipCode = MutableLiveData<String>()
    val zipCode: LiveData<String> get() = _zipCode

    private val _skills = MutableLiveData<List<String>>()
    val skills: LiveData<List<String>> get() = _skills

    private val _preferences = MutableLiveData<String>()
    val preferences: LiveData<String> get() = _preferences

    private val _availability = MutableLiveData<List<String>>()
    val availability: LiveData<List<String>> get() = _availability

    fun updateProfile(
        fullName: String,
        address1: String,
        address2: String,
        city: String,
        state: String,
        zipCode: String,
        skills: List<String>,
        preferences: String,
        availability: List<String>
    ) {
        _fullName.value = fullName
        _address1.value = address1
        _address2.value = address2
        _city.value = city
        _state.value = state
        _zipCode.value = zipCode
        _skills.value = skills
        _preferences.value = preferences
        _availability.value = availability
    }
}
