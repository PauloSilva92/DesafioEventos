package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Checkin
import com.example.desafioandroideventos.data.network.EventsAPI
import com.example.desafioandroideventos.data.network.Status
import java.io.IOException

class CheckinSender (val api: EventsAPI) {
    fun checkin(checkin: Checkin): CheckinResult {
        try {
            val result = api.checkin(checkin).execute()
            if (result.isSuccessful) return CheckinResult(Status.SUCCESS)
            return CheckinResult(Status.FAILURE)
        } catch (exception: IOException) {
            exception.printStackTrace()
            return CheckinResult(Status.NETWORK_ERROR)
        }

    }
}