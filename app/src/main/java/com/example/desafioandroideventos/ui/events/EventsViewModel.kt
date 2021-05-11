package com.example.desafioandroideventos.ui.events

import androidx.lifecycle.ViewModel
import com.example.desafioandroideventos.data.repositories.EventRepository

class EventsViewModel : ViewModel() {
    private val eventRepository = EventRepository()

    fun getEvents() = eventRepository.getEvents()
    fun getEventById(idEvent: Long) = eventRepository.getEventById(idEvent)

}