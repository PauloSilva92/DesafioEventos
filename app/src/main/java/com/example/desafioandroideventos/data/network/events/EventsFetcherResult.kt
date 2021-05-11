package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Event

class EventsFetcherResult (
    var status: EventsFetcher.Status,
    val events: List<Event>?
        )
