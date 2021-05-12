package com.example.desafioandroideventos.ui.events

import android.widget.ImageView
import com.example.desafioandroideventos.data.models.Event

interface EventSelectedListener {
    fun onEventSelectedListener(event: Event, eventImage: ImageView)
}