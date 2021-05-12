package com.example.desafioandroideventos.ui.events

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafioandroideventos.R
import com.example.desafioandroideventos.data.models.Event

class EventsAdapter(val events: List<Event>, val context: Context, val eventSelectedListener: EventSelectedListener):
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    class EventsViewHolder(itemView: View, context: Context, private val eventSelectedListener: EventSelectedListener): RecyclerView.ViewHolder(itemView) {
        private val eventTitle: TextView = itemView.findViewById(R.id.event_title)
        private val eventDate: TextView = itemView.findViewById(R.id.event_date)
        private val eventDescription: TextView = itemView.findViewById(R.id.event_description)
        private val eventImage: ImageView = itemView.findViewById(R.id.event_image)
        private val eventDetailsButton: Button = itemView.findViewById(R.id.event_button_details)

        fun bindEvent(event: Event) {
            eventTitle.text = event.title
            eventDate.text = event.formattedDate
            eventDescription.text = event.description

            eventImage.apply {
                Glide.with(context)
                    .load(event.image)
                    .error(R.drawable.placeholder)
                    .into(this)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    transitionName = event.image
                }
            }

            eventDetailsButton.setOnClickListener {
                eventSelectedListener.onEventSelectedListener(event, eventImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventsViewHolder(view, context, eventSelectedListener)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event: Event = events[position]
        holder.bindEvent(event)
    }

    override fun getItemCount(): Int = events.size
}