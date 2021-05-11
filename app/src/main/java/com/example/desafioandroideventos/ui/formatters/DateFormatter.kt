package com.example.desafioandroideventos.ui.formatters

import java.text.SimpleDateFormat
import java.util.*


open class DateFormatter: Formatter {
    override fun format(value: String): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(value.toLong() * 1000)
        return simpleDateFormat.format(date)
    }


}