package com.example.employeeapp.network

import com.example.employeeapp.model.UserCredentials
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/firestore/register")
    fun registerUser(@Body credentials: UserCredentials): Call<String>

    @POST("/api/firestore/login")
    fun loginUser(@Body credentials: UserCredentials): Call<String>
}
