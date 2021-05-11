package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Event

class EventFetcherResult(
    var status: EventsFetcher.Status,
    var event: Event?
)
