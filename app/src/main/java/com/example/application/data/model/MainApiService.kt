package com.example.application.data.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("register")
    suspend fun registerUser(@Body user: User): Response<String>

    @POST("auth")
    suspend fun authUser(@Body user: User): Response<String>
}