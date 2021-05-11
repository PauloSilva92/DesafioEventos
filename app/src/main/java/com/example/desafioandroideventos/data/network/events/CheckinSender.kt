package com.example.desafioandroideventos.data.network.events

class CheckinSender {
    fun checkin(): CheckinResult {

        return CheckinResult(EventsFetcher.Status.SUCCESS)
    }
}