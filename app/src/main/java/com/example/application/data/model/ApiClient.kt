package com.example.application.data.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val gson: Gson  = GsonBuilder()
    .setLenient()
    .create()

object ApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:8080/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val userService: UserService = retrofit.create(UserService::class.java)
}