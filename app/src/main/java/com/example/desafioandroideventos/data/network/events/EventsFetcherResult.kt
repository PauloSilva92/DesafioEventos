package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Event
import com.example.desafioandroideventos.data.network.Status

class EventsFetcherResult (
    var status: Status,
    val events: List<Event>?
        )
