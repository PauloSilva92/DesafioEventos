package com.example.desafioandroideventos.data.network

import com.example.desafioandroideventos.data.models.Checkin
import com.example.desafioandroideventos.data.models.Event
import retrofit2.Call
import retrofit2.http.*

interface EventsAPI {
    @GET("/api/events")
    fun getEvents(): Call<List<Event>>

    @GET("/api/events/{id}")
    fun getEvent(
        @Path("id") id: Long
    ): Call<Event>

    @POST("")
    fun checkin(
        @Body checkin: Checkin
    )
}