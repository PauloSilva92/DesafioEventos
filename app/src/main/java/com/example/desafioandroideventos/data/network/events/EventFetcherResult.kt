package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Event
import com.example.desafioandroideventos.data.network.Status

class EventFetcherResult(
    var status: Status,
    var event: Event?
)
