package com.example.desafioandroideventos.ui.events

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioandroideventos.R
import com.example.desafioandroideventos.data.models.Event
import com.example.desafioandroideventos.ui.details.EventDetailsFragmentArgs

class EventsFragment : Fragment() {


    private val events = ArrayList<Event>()

    private val viewModel: EventsViewModel by lazy {
        ViewModelProvider(this).get(EventsViewModel::class.java)
    }

    private val eventsAdapter: EventsAdapter by lazy {
        EventsAdapter(events, requireContext(), object : EventSelectedListener {
            override fun onEventSelectedListener(event: Event, eventImage: ImageView) {
                val fragmentNavigatorExtras = FragmentNavigatorExtras(eventImage to event.image)
                val action = EventsFragmentDirections.actionMainFragmentToEventDetailsFragment(uri = event.image, eventId = event.id)
                findNavController().navigate(action, fragmentNavigatorExtras)
            }
        })
    }

    private lateinit var recyclerViewEvents: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewEvents = view.findViewById(R.id.recyclerview_events)
        recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext()).apply { orientation = RecyclerView.VERTICAL }
        recyclerViewEvents.adapter = eventsAdapter

        viewModel.getEvents().subscribe({
            events.clear()
            events.addAll(it)
            eventsAdapter.notifyDataSetChanged()
        },
            { error -> Log.d("EVENTS", error.localizedMessage!!) },
            {
                Log.d("EVENTS", "COMPLETED")
            })


    }

}