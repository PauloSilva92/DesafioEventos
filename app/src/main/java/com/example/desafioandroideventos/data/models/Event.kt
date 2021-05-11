package com.example.desafioandroideventos.data.models

import com.example.desafioandroideventos.ui.formatters.DateFormatter


data class Event (
    var people: List<People>,
    var date: Long,
    var description: String,
    var image: String,
    var longitude: Double,
    var latitude: Double,
    var price: Double,
    var title: String,
    var id: Long
){
    val formattedDate: String get() {
        return DateFormatter().format(date.toString())
    }
}
