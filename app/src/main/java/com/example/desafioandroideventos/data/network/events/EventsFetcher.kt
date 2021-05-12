package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.network.EventsAPI
import com.example.desafioandroideventos.data.network.Status
import java.io.IOException
import java.lang.Exception

class EventsFetcher (val eventsAPI: EventsAPI) {

    fun fetchEvents(): EventsFetcherResult {
        try {
            val result =  eventsAPI.getEvents().execute()
            if (result.isSuccessful) {
                return EventsFetcherResult(Status.SUCCESS, result.body())
            }
            return EventsFetcherResult(Status.FAILURE, null)
        } catch (excpetion: IOException) {
            return EventsFetcherResult(Status.NETWORK_ERROR, null)
        }
    }


    fun fetchEventById(id: Long): EventFetcherResult {
        try {
            val result = eventsAPI.getEvent(id).execute()
            if (result.isSuccessful) {
                return EventFetcherResult(Status.SUCCESS, result.body()!!)
            }
            return EventFetcherResult(Status.FAILURE, null)
        } catch (exception: Exception) {
            return EventFetcherResult(Status.NETWORK_ERROR, null)
        }
    }
}